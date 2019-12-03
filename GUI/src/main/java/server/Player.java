package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Player implements Runnable {
	
	private Socket socket;
	private Scanner in;
	private InputStream inStream;
	private PrintWriter out;
	
	private Server myServer;
	
	private Board board;
	private Player opponent;
	private char color = 'B';
	
	private String getMessage;
	
	public Player(Socket socket, Server myServer) {
		this.socket = socket;
		try {
			in = new Scanner( socket.getInputStream() );
			this.inStream = socket.getInputStream();
			out = new PrintWriter( socket.getOutputStream() ); 
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		this.myServer = myServer;
	}
	
	public void setOpponent( Player opponent, Board board ) {
		this.opponent = opponent;
		this.board = board;
		this.opponent.notify();
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
	
	public void interpretClientCommand( String clientCommand ) {
		if( clientCommand.compareTo("exit") == 0 ) {
			//TODO: inform opponent about exit and exit safely
		}
		else {
			//TODO: check player move correctness
		}
	}
	
	private void startGame() {
		
		Thread opponentListener = new OpponentListener();
		Thread clientListener = new ClientListener();
		opponentListener.start();
		clientListener.start();
		
		while(true) {
			synchronized( getMessage ) {
				try {
					getMessage.wait();
				} catch (InterruptedException e) {
					System.out.println("Player interrupted waiting for message");
				}
			}
			
			if( getMessage.compareTo("fromClient") == 0 ) {
				interpretClientCommand( getClientCommand() );
				getMessage = null;
			}
			else {
				// TODO: interpret opponent command
			}
		}
	}
	
	public void run() {
		
		// wait for client initializing command
		while( !hasClientSendCommand() );
		
		String[] StartConfiguration = getClientCommand().split(" ");
		int boardSize = Integer.parseInt( StartConfiguration[0] );
		
		// configuration if pvp game
		if( StartConfiguration[1].compareTo("p") == 0 ) {
			
			synchronized(myServer.waitingPlayers) {
				if( myServer.waitingPlayers.getWaitingPlayer(boardSize) == null ) myServer.waitingPlayers.addWaitingPlayer(this, boardSize);
				else {
					board = new Board(9);
					color = 'W';
					myServer.waitingPlayers.getWaitingPlayer(boardSize).setOpponent(this, board);
					myServer.waitingPlayers.removeWaitingPlayer(boardSize);
				}
			}

			if( opponent == null ) {
				synchronized( opponent ) {
					try {
						opponent.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
			}
			sendCommand( Character.toString(color) );
			startGame();
		}
	}
	
	class ClientListener extends Thread{
		public void run() {
			
			Thread thisThread = Thread.currentThread();
			
			while( !thisThread.isInterrupted() ) {
				
				if( getMessage.compareTo("fromClient") != 0 && hasClientSendCommand() ) {
					getMessage = "fromClient";
					getMessage.notify();
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
				
				synchronized( board ) {
					try {
						board.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				
				getMessage = "fromOpponent";
				getMessage.notify();
				
			}
		}
	}
}
