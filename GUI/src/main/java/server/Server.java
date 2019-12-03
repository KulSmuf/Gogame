package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server {
	
	public WaitingPlayerList waitingPlayers = new WaitingPlayerList(); 
	
	public Server() throws IOException{
		run();
	}
	
	private void run() throws IOException {
		System.out.println("Server run");
        try (ServerSocket listener = new ServerSocket(50000)) {
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Player( listener.accept(),this ));
            }
        }
	}
	
	public static void main(String[] args) throws Exception {
		Server server = new Server();
    }
}
