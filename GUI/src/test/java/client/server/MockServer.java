package client.server;

import java.io.IOException;

import server.Server;

class MockServer extends Thread{
	public void run() {
		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}