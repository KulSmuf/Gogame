package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static void main(String[] args) throws Exception {
		System.out.println("Server run");
        try (ServerSocket listener = new ServerSocket(50000)) {
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Player( listener.accept() ));
            }
        }
    }
}
