package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private Socket socket;
	private Scanner in;
	private InputStream inStream;
	private PrintWriter out;
	
	public Client() throws Exception {
		socket = new Socket( "localhost", 50000 );
		this.in = new Scanner( socket.getInputStream() );
		this.inStream = socket.getInputStream();
		this.out = new PrintWriter( socket.getOutputStream() );
	}
	
	public boolean hasServerSendCommand() throws IOException {
		return inStream.available() > 0;
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