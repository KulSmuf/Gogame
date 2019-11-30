package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Player implements Runnable {
	
	Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	public Player(Socket socket) {
		this.socket = socket;
		try {
			in = new Scanner( socket.getInputStream() );
			out = new PrintWriter( socket.getOutputStream() ); 
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}


	public void run() {
		
	}

}
