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
	
	public Client() throws Exception {
		socket = new Socket( "localhost", 50000 );
		this.in = new Scanner( socket.getInputStream() );
		this.out = new PrintWriter( socket.getOutputStream() );
	}
	
	public boolean hasServerSendCommand() {
		return in.hasNextLine();
	}
	
	public String getServerCommand() {
		String respond = in.nextLine();
		return respond;
	}
	
	public void sendCommand( String command ) {
		out.println(command);
		out.flush();
	}
}