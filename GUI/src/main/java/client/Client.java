package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	public Client() throws Exception {
		socket = new Socket( "localhost", 50000 );
		this.in = new Scanner( socket.getInputStream() );
		this.out = new PrintWriter( socket.getOutputStream() );;
	}
}