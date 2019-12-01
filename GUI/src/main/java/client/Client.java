package client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	/* mozliwe komendy:
	 * start(9,13,19)(p/b), po wybraniu rozmiaru i czy gra z botem czy nie
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public Client() throws Exception {
		socket = new Socket( "localhost", 50000 );
		this.in = new Scanner( socket.getInputStream() );
		this.out = new PrintWriter( socket.getOutputStream() );
	}
	public String SendCom( String command ) {
		out.println(command); out.flush();
		String ret = in.nextLine(); 
		return ret;
	}
}