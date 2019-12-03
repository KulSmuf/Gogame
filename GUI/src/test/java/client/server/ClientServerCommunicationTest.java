package client.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import client.Client;
import server.Server;

public class ClientServerCommunicationTest {

	@Test
	public void connectionTest() throws Exception {
		
		MockServer mockServer = new MockServer();
		mockServer.start();
		
		Client player1 = new Client();
		player1.sendCommand("9 p");
		
		Client player2 = new Client();
		player2.sendCommand("9 p");
		
		while( !player1.hasServerSendCommand() );
		System.out.println("command from server " + player1.getServerCommand());
		
		while( !player2.hasServerSendCommand() );
		System.out.println("command from server " + player2.getServerCommand());
	}
}
