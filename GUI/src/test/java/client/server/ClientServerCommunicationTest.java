package client.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import client.Client;
import server.Server;

public class ClientServerCommunicationTest {

	@Test
	public void connectionTest() throws Exception {
		
		MockServer mockServer = new MockServer();
		mockServer.start();
		
		Client client = new Client();		
		client.sendCommand("ask");
		
		while( !client.hasServerSendCommand() );
		assertEquals( "respond",client.getServerCommand() );
	}
	
	class MockServer extends Thread{
		public void run() {
			try {
				Server mockServer = new Server();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
