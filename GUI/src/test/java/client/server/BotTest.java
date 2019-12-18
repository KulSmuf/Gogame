package client.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import client.Client;
import server.Bot;

public class BotTest {

	@Test
	public void testBot() throws Exception {
		MockServer mockServer = new MockServer();
		mockServer.start();
				
		Client player = new Client();
		player.sendCommand("9 b");
		
		Thread.sleep(100);
		
		while( !player.hasServerSendCommand() ); 
		String respond = player.getServerCommand(); 
		assertEquals( "B" , respond);
			
		Thread.sleep(100);
		
		//   Ruch gracza pierwszego
		String move = "0 0";
		player.sendCommand(move);
		while( !player.hasServerSendCommand() );
		assertEquals( "1" , player.getServerCommand());
		
		while( !player.hasServerSendCommand() );
		System.out.println("bot move: " + player.getServerCommand() + "\n");
		
		move = "2 2";
		player.sendCommand(move);
		while( !player.hasServerSendCommand() );
		assertEquals( "1" , player.getServerCommand());
		
		while( !player.hasServerSendCommand() );
		System.out.println("bot move: " + player.getServerCommand()+ "\n");
		
		move = "2 1";
		player.sendCommand(move);
		while( !player.hasServerSendCommand() );
		assertEquals( "1" , player.getServerCommand());
		
		while( !player.hasServerSendCommand() );
		System.out.println("bot move: " + player.getServerCommand()+ "\n");
		
		move = "4 4";
		player.sendCommand(move);
		while( !player.hasServerSendCommand() );
		assertEquals( "1" , player.getServerCommand());
		
		while( !player.hasServerSendCommand() );
		System.out.println("bot move: " + player.getServerCommand()+ "\n");
	}
}
