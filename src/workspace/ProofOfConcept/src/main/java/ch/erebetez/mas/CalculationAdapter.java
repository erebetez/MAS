package ch.erebetez.mas;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalculationAdapter implements JavaDelegate {

	public void execute(DelegateExecution execution) {

		String host = "localhost";
		int port = 9001;

		System.out.println("sending to calculator at " + host + ":" + port);

		Integer lngB = (Integer) execution.getVariable("varB");
		Long dilutionFactor = (Long) execution.getVariable("dilutionFactor");

		// very primitive stuff...
		String payload = lngB.toString() + "::" + dilutionFactor.toString();

		String returnValue = "none";

		try {
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;

			echoSocket = new Socket(host, port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));

			// while (in!=null) {
			try {

				out.println(payload);

				returnValue = in.readLine();

				System.out.println("echo from server: '" + returnValue + "'");

			} catch (Exception e) {
				System.out.println("Fehler " + e.getMessage()
						+ " auf dem Client:");
				System.out.println(e.getStackTrace());
				// break;
			}
			// }

			out.close();
			in.close();
			echoSocket.close();

		} catch (Exception e) {
			System.out.println("Fehler " + e.getMessage()
					+ " beim initalisieren:");
			System.out.println(e.getStackTrace());

		}

		execution.setVariable("varResult", returnValue);

	}
}