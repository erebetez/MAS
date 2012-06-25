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

package ch.erebetez.calcclient;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import ch.erebetez.decode.Compress.*;
import ch.erebetez.decode.UnCompress.*;
import ch.erebetez.marshalling.*;
import ch.erebetez.xmlutils.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.Map;
import java.util.UUID;


public class ExternAppAdapter implements JavaDelegate {

	private String host = "";
	private Integer port = 5555;
	private String application;
	
	// http://forums.activiti.org/en/viewtopic.php?t=1662
//	private Expression application;

	private final String separator = "::";	
	
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(Integer port) {
		this.port = port;
	}


	public void execute(DelegateExecution execution) throws Exception{

		System.out.println("sending to app at " + host + " : " + port);

		@SuppressWarnings("unchecked")
		Map<String, String> sessionVariable = (Map<String, String>) execution
				.getVariable("sessionVariable");

		EatMarshaller marshall = new EatMarshaller();
		String xmlString = marshall.marshall(sessionVariable);

		Eatxml eatxml = new Eatxml(xmlString);

		String shortxml = null;
		try {
			shortxml = eatxml.fromLongToShortXml();
		} catch (TransformationError e) {
			e.printStackTrace();
		}

		Compress comp = new CompressEat(shortxml);

		String uuid = UUID.randomUUID().toString();

		// excpected UUID::MarshalString::Axxx
		StringBuilder payload = new StringBuilder();
		payload.append(uuid);
		payload.append(separator);
		payload.append(comp.compress());
		payload.append(separator);
		payload.append(application);

		Map<String, String> serverReturn = null;
		String returnValue = "none";
		
		try {
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;

			echoSocket = new Socket(host, port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));

			out.println(payload.toString());

			while (in != null) {
				try {

					returnValue = in.readLine();

					System.out.println("echo from server: '" + returnValue
							+ "'");

					String[] parameterList = returnValue.split(separator);

					if (parameterList.length > 0
							&& parameterList[0].equals(uuid)) {

						if (parameterList[2].equals("FINISHED")) {
							System.out.println("regain Data");
							serverReturn = regainData(parameterList[1]);

							out.println(finishedSignal(uuid));
							break;
						}

					}

				} catch (Exception e) {
					System.out.println("Fehler " + e.getMessage()
							+ " auf dem Client:");
					System.out.println(e.getStackTrace());
					break;
				}
			}

			out.close();
			in.close();
			echoSocket.close();

		} catch (Exception e) {
			System.out.println("Fehler " + e.getMessage()
					+ " beim initalisieren:");
			System.out.println(e.getStackTrace());
			
			throw new BpmnError("CalculationServerNotAvailable");
		}

		System.out.println("Transaction finished" + serverReturn);

		if (serverReturn != null) {
			execution.setVariable("sessionVariable", serverReturn);
		}
	}
	
	private Map<String, String> regainData(String marshall) {

		UnCompress uncomp = new UnCompressEat(marshall);
		Eatxml eatxml = new Eatxml();
		eatxml.setXml(uncomp.uncompress());

		try {
			eatxml.fromShortToLongXml();
		} catch (TransformationError e1) {
			e1.printStackTrace();
		}

		EatUnmarshaller	unmarshall = new EatUnmarshaller(eatxml.getXml());
		return (Map<String, String>) unmarshall.getEatObject();
	}

	private String finishedSignal(String uuid) {
		StringBuilder builder = new StringBuilder();
		builder.append(uuid);
		builder.append(separator);
		builder.append("OK");
		return builder.toString();
	}
	

//	public void execute(DelegateExecution execution) throws Exception {
//	    try {
//	      executeBusinessLogic();
//	    } catch (BusinessExeption e) {
//	      throw new BpmnError("BusinessExeptionOccured");
//	    }
//	  }

	
}