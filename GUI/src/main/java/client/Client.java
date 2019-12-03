package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import GameOfGo.GUI.GUI;

public class Client {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private GUI gui;
	
	public Client(GUI gui) throws Exception {
		socket = new Socket( "localhost", 50000 );
		this.in = new Scanner( socket.getInputStream() );
		this.out = new PrintWriter( socket.getOutputStream() );
		this.gui = gui;
	}
	
	public String sendCom( String command ) {
		out.println(command); out.flush();
		
		while( !in.hasNextLine() );
		String respond = in.nextLine();
		
		return respond;
	}

	class CustomThread extends Thread {
		@Override
		public void run() {
			
		}
	}
}