package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import board.Board;

class Player implements Runnable {

	private Scanner in;
	private InputStream inStream;
	private PrintWriter out;
	
	private Server myServer;
	
	protected  Board board;
	protected Player opponent;
	protected char color = 'B';
	
	public Object flag = new Object();
	
	protected Message message = new Message(" ");
	
	public Player(Socket socket, Server myServer) {
		try {
			in = new Scanner( socket.getInputStream() );
			this.inStream = socket.getInputStream();
			out = new PrintWriter( socket.getOutputStream() ); 
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		this.myServer = myServer;
	}
	
	public Player() {
		// for Bot
	}
	
	public void setOpponent( Player opponent, Board board ) {
		this.opponent = opponent;
		this.board = board;
		synchronized(message) {
			message.notify();
		}
	}
	
	public void sendCommand( String command ) {
		out.println(command);
		out.flush();
	}
	
	public boolean hasClientSendCommand() {
		try {
			return inStream.available() > 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getClientCommand() {
		String respond = in.nextLine();
		return respond;
	}
	
	public int[] parseCords( String[] stringCords ) {
		int[] ret = { Integer.parseInt(stringCords[0]), Integer.parseInt(stringCords[1]) };
		return ret;
	}
	
	public boolean interpretClientCommand( String clientCommand ) {
		if( clientCommand.compareTo("exit") == 0 ) {
			board.setMessage("exit");
			synchronized( opponent.flag ) {
				opponent.flag.notify();
			}
			return true;
		}
		else if ( clientCommand.compareTo("pass") == 0 ) {
			boolean end = board.pass();
			if( end ) {
				String score = board.countTerritory();
				board.setMessage(score);
				sendCommand(score);
				synchronized( opponent.flag ) {
					opponent.flag.notify();
				}
				return true;
			}
			else board.setMessage("pass");
			synchronized( opponent.flag ) {
				opponent.flag.notify();
			}
		}
		else{
			int[] cords = parseCords( clientCommand.split(" ") );
			
			if( board.checkCorrectness( cords[0], cords[1] ) ) {
				sendCommand( board.makeMove( cords[0], cords[1] ) );
				synchronized( opponent.flag ) {
					opponent.flag.notify();
				}
			}
			else sendCommand("0");
		}
		return false;
	}
	
	protected void startGame() {
				
		Thread clientListener = new ClientListener();
		Thread opponentListener = new OpponentListener();
		opponentListener.start();
		clientListener.start();
		
		while(true) {
			synchronized( message ) {
				try {
					message.wait();
				} catch (InterruptedException e) {
					System.out.println("Player interrupted waiting for message");
				}
			}
			
			if( message.getMessage().compareTo("fromClient") == 0 ) {
				if( interpretClientCommand( getClientCommand() ) ) return;
				message.setMessage(" ");
			}
			else {
				sendCommand( board.getMessageLog() ); 
				if( board.getMessageLog().compareTo("exit") == 0 ) return;
			}
		}
	}
	
	public void run() {
		
		// wait for client initializing command
		while( !hasClientSendCommand() );
		
		String[] StartConfiguration = getClientCommand().split(" ");
		int boardSize = Integer.parseInt( StartConfiguration[0] );
		
		Thread clientListener = new ClientListener();
		
		// configuration if pvp game
		if( StartConfiguration[1].compareTo("p") == 0 ) {
			
			synchronized(myServer.waitingPlayers) {
				if( myServer.waitingPlayers.getWaitingPlayer(boardSize) == null ) myServer.waitingPlayers.addWaitingPlayer(this, boardSize);
				else {
					opponent = myServer.waitingPlayers.getWaitingPlayer(boardSize);
					myServer.waitingPlayers.removeWaitingPlayer(boardSize);
				}
			}

			if( opponent == null ) {
				clientListener.start();
				synchronized( message ) {
					try {
						message.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				clientListener.interrupt();
				if(message.getMessage().compareTo("exit") == 0) {
					synchronized(myServer.waitingPlayers) {
						myServer.waitingPlayers.removeWaitingPlayer(boardSize);
					}
					return;
				}
			}
			else {
				board = new Board( boardSize );
				color = 'W';
				opponent.setOpponent(this, board);
			}
			sendCommand( Character.toString(color) );
			startGame();
		}
		else {
			board = new Board( boardSize );
			opponent = new Bot();
			(new Thread(opponent)).start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			opponent.setOpponent(this, board);
			sendCommand( Character.toString(color) );
			startGame();
		}
	}
	
	class ClientListener extends Thread{
		public void run() {
			
			Thread thisThread = Thread.currentThread();
			
			while( !thisThread.isInterrupted() ) {
				
				if( message.getMessage().compareTo("fromClient") != 0 && hasClientSendCommand() ) {
					synchronized(message) {
						message.setMessage("fromClient");
						message.notify();
					}
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
				
			}
		}
	}
	
	class OpponentListener extends Thread{
		public void run() {
			
			Thread thisThread = Thread.currentThread();
			
			while( !thisThread.isInterrupted() ) {
				
				synchronized( flag ) {
					try {
						flag.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				
				message.setMessage("fromOpponent");
				synchronized( message ) {
					message.notify();
				}
				
			}
		}
	}
	
	class Message{
		String message;
		
		Message(String message){
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}	
	}
}
