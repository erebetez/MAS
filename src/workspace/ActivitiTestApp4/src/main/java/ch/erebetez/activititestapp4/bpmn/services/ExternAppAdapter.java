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

package ch.erebetez.activititestapp4.bpmn.services;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.xml.sax.SAXException;

import ch.erebetez.activititestapp4.utils.compress.Compress;
import ch.erebetez.activititestapp4.utils.compress.CompressEat;
import ch.erebetez.activititestapp4.utils.marshall.EatMarshaller;
import ch.erebetez.activititestapp4.utils.marshall.Eatxml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

public class ExternAppAdapter implements JavaDelegate {

	private Expression host;	
	private Expression port;
	
	
	public void setHost(Expression host) {
		this.host = host;
	}


	public void setPort(Expression port) {
		this.port = port;
	}


	public void execute(DelegateExecution execution) {

		System.out.println("sending to app at " + host.getExpressionText() + " : " + port.getExpressionText());

		Map<String,String> itemData = (Map<String,String>) execution.getVariable("itemData");
		
		EatMarshaller marshall = new EatMarshaller();
		String xmlString = marshall.marshall(itemData);
		
		System.out.println(xmlString);
		
		Eatxml eatxml;
		String payload = "none";
		String returnValue = "none";
		
		try {
			eatxml = new Eatxml();

			eatxml.setXml(xmlString);
	
			eatxml.fromLongToShortXml();
			
			String shortxml = eatxml.getXml();			
						
			Compress comp = new CompressEat(shortxml);
			
			payload = comp.compress();			
						
		} catch (ParserConfigurationException e1) {

			e1.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	

		try {
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;

			echoSocket = new Socket(host.getExpressionText(), Integer.parseInt(port.getExpressionText()) );
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