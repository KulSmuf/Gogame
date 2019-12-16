package client.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import client.Client;

public class ClientServerCommunicationTest {

	@Test
	public void testClientServerCommunication() throws Exception {
		MockServer mockServer = new MockServer();
		mockServer.start();
				
		Client player1 = new Client();
		player1.sendCommand("9 p");
		
		Thread.sleep(100);
		
		Client player2 = new Client();
		player2.sendCommand("9 p");
				
		while( !player1.hasServerSendCommand() ); 
		String respond = player1.getServerCommand(); 
		assertEquals( "B" , respond);
				
		while( !player2.hasServerSendCommand() );
		respond = player2.getServerCommand();
		assertEquals( "W" , respond);
		
		//   Ruch gracza pierwszego
		String move = "0 0";
		player1.sendCommand(move);
		while( !player1.hasServerSendCommand() );
		assertEquals( "1" , player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		assertEquals( "0,0 0" , player2.getServerCommand());
		
		//   Ruch gracza drugiego
		move = "0 1";
		player2.sendCommand(move);
				
		while( !player1.hasServerSendCommand() );
		assertEquals( "0,1 0" , player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		assertEquals( "1" , player2.getServerCommand());
		
		move = "exit";
		player2.sendCommand(move);
		while( !player1.hasServerSendCommand() );
		assertEquals( "exit" ,player1.getServerCommand() );
	}
	
	@Test
	public void testClientServerCommunication2() throws Exception {
		Client player1 = new Client();
		player1.sendCommand("9 p");
		
		Thread.sleep(100);
		
		Client player2 = new Client();
		player2.sendCommand("9 p");
				
		while( !player1.hasServerSendCommand() ); 
		String respond = player1.getServerCommand(); 
		assertEquals( "B" , respond);
		
		
		
		while( !player2.hasServerSendCommand() );
		respond = player2.getServerCommand();
		assertEquals( "W" , respond);
		
		
		//   Ruch gracza pierwszego
		String move = "0 0";
		player1.sendCommand(move);
		while( !player1.hasServerSendCommand() );
		assertEquals( "1" , player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		assertEquals( "0,0 0" , player2.getServerCommand());
		
		//   Ruch gracza drugiego
		move = "pass";
		player2.sendCommand(move);
		
		while( !player1.hasServerSendCommand() );
		assertEquals( "pass" , player1.getServerCommand());
		
		move = "0 1";
		player1.sendCommand(move);
		while( !player1.hasServerSendCommand() );
		assertEquals( "1" , player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		assertEquals( "0,1 0" , player2.getServerCommand());
		
		move = "pass";
		player2.sendCommand(move);
		
		while( !player1.hasServerSendCommand() );
		assertEquals( "pass" , player1.getServerCommand());
		
		move = "pass";
		player1.sendCommand(move);
		
		while( !player2.hasServerSendCommand() );
		assertEquals( "pass" , player2.getServerCommand());
	}
}
