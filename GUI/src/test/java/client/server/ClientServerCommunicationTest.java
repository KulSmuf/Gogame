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
		System.out.println("command from server to player1 " + player1.getServerCommand());
		
		while( !player2.hasServerSendCommand() );
		System.out.println("command from server to player2 " + player2.getServerCommand());
		
		//   Ruch gracza pierwszego
		String move = "0 0";
		player1.sendCommand(move); System.out.println("player1 move: " + move);
		
		while( !player1.hasServerSendCommand() );
		while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
		System.out.println();
		
		//   Ruch gracza drugiego
		move = "0 1";
		player2.sendCommand(move); System.out.println("player2 move: " + move);
		
		while( !player1.hasServerSendCommand() );
		while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
		System.out.println();
		
		//   Ruch gracza pierwszego
		move = "1 1";
		player1.sendCommand(move); System.out.println("player1 move: " + move);
		
		while( !player1.hasServerSendCommand() );
		while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
		System.out.println();
		
		//   Ruch gracza drugiego
		move = "1 0";
		player2.sendCommand(move); System.out.println("player2 move: " + move);
		
		while( !player1.hasServerSendCommand() );
		while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
		while( !player2.hasServerSendCommand() );
		while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
		System.out.println();
		
		//   Zły ruch gracza pierwszego
		move = "0 0";
		player1.sendCommand(move); System.out.println("player1 move: " + move);
		
		while( !player1.hasServerSendCommand() );
		while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
		while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
		System.out.println();
		
	//   Ruch gracza pierwszego
			move = "0 2";
			player1.sendCommand(move); System.out.println("player1 move: " + move);
			
			while( !player1.hasServerSendCommand() );
			while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
			while( !player2.hasServerSendCommand() );
			while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
			System.out.println();
			
		//   Ruch gracza drugiego
			move = "2 0";
			player2.sendCommand(move); System.out.println("player2 move: " + move);
			
			while( !player1.hasServerSendCommand() );
			while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
			while( !player2.hasServerSendCommand() );
			while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
			System.out.println();
			
		//   Ruch gracza pierwszego
			move = "0 0";
			player1.sendCommand(move); System.out.println("player1 move: " + move);
			
			while( !player1.hasServerSendCommand() );
			while(  player1.hasServerSendCommand() ) System.out.println("command from server to player1 " + player1.getServerCommand());
			while( !player2.hasServerSendCommand() );
			while(  player2.hasServerSendCommand() ) System.out.println("command from server to player2 " + player2.getServerCommand());
			System.out.println();
			
			player2.sendCommand("exit");
			player1.sendCommand("exit");
	}
}
