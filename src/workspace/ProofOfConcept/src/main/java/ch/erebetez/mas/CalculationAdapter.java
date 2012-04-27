/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
    Simple client for an excle calcultion server.

    Copyright (C) 2012  Etienne Rebetez

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */

package ch.erebetez.mas;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalculationAdapter implements JavaDelegate {

	public void execute(DelegateExecution execution) {

		String host = (String) execution.getVariable("calcHost");
		Integer port = (Integer) execution.getVariable("calcPort");

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

			echoSocket = new Socket(host, port.intValue());
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