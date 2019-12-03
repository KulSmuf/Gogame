package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Player implements Runnable {
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Server myServer;
	
	public Player(Socket socket, Server myServer) {
		this.socket = socket;
		try {
			in = new Scanner( socket.getInputStream() );
			out = new PrintWriter( socket.getOutputStream() ); 
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		this.myServer = myServer;
	}

	public void run() {
		while(true) {
			if( in.hasNextLine() ) {
				in.nextLine();
				out.println("respond");
				out.flush();
			}
		}
	}
}
