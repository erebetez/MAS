package ch.erebetez.calcserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcServerSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = null;
		serverSocket = new ServerSocket(9001);
		try {

			Socket clientSocket = null;
			while (serverSocket != null) {
				clientSocket = serverSocket.accept();

				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream(), true);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));

				String inputLine, outputLine;

				while ((inputLine = in.readLine()) != null) {
					System.out.println("got: " + inputLine);

					outputLine = inputLine;

					out.println("response::" + outputLine);
				}
				out.close();
				in.close();
				clientSocket.close();
			}

			serverSocket.close();
		} catch (Exception e) {
			System.out.println("Fehler" + e.getMessage() + " auf dem Server:");
			System.out.println(e.getStackTrace());
			serverSocket.close();

		}
	}

}
