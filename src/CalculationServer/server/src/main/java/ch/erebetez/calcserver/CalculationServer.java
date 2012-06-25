package ch.erebetez.calcserver;

import java.io.IOException;
import java.net.ServerSocket;

public class CalculationServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		RequestHandler requestHandler = new RequestHandler();
		
		try {
			serverSocket = new ServerSocket(9998);
			System.out.println("Calculation Server started");
			System.out.println("Listening on port 9998");
		} catch (IOException e) {
			System.err.println("Could not listen on port: 9998.");
			System.exit(-1);
		}

		while (listening) {
			requestHandler.newRequest(serverSocket.accept());			
		}
		
		serverSocket.close();
	}
}
