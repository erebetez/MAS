package ch.erebetez.calcserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import ch.erebetez.eatstarter.excel.EatStarter;
import ch.erebetez.eatstarter.util.FinishedListener;

public class Request extends Thread implements FinishedListener {
	private Socket socket = null;
	private RequestHandler handler = null;

	private Path marshllPayloadPath = FileSystems.getDefault().getPath("C:",
			"temp");

	private final String fileSeparator = FileSystems.getDefault()
			.getSeparator();
	private final String dataSeparator = "::"; // FIXME Put in an Interface?

	private EatStarter excel = null;

	private String uuid;

	List<String> eatParameter = null;

	public Request(Socket socket, RequestHandler handler) {
		super("RequestThread");
		this.socket = socket;
		this.handler = handler;

		processInput();
	}

	public void processInput() {

		BufferedReader in;

		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// excpected UUID::MarshalString::Axxx
			String[] parameterList = in.readLine().split(dataSeparator);

			if (parameterList.length != 3) {
				throw new IllegalArgumentException(
						"got wrong arguments numer from client.");
			}

			// TODO Make some checks etc.
			uuid = parameterList[0];

			writeMarshallString(parameterList[1]);

			eatParameter = new Vector<String>();
			eatParameter.add(parameterList[2]);
			eatParameter.add(uuid);

			System.out.println("got " + uuid);
			System.out.println("got " + parameterList[2]);
			System.out.println("got " + parameterList[1]);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void writeMarshallString(String marshall) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					marshllPayloadPath.toString() + fileSeparator + uuid));
			out.write(marshall);
			out.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public void run() {
		if (eatParameter == null) {
			return;
		}

		excel = new EatStarter();
		excel.addListener(this);
		excel.startEat(eatParameter);
	}

	public void finished() {
		StringBuilder marshall = new StringBuilder();
		BufferedReader in;
		PrintWriter out;
		try {
			// UUID::MarshalString::FINISHED
			marshall.append(uuid);
			marshall.append(dataSeparator);
			marshall.append(readMarshallString(marshllPayloadPath.toString() + fileSeparator
					+ this.uuid, "windows-1252"));
			marshall.append(dataSeparator);
			marshall.append("FINISHED");
			
			out = new PrintWriter(this.socket.getOutputStream(), true);	
			out.println(marshall.toString());

			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// excpected UUID::OK
			String value = in.readLine();
			
			System.out.println(value);
			
			String[] parameterList = value.split(dataSeparator);
			
			if(parameterList[0].equals(uuid) && parameterList[1].equals("OK")){
				File file = new File(marshllPayloadPath.toString() + fileSeparator
						+ this.uuid);
				file.delete();
			}
			
			out.close();
			in.close();

			socket.close();

			System.out.println("Socked closed");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Tell handler request is finished.
		handler.finished();
	}

	private String readMarshallString(String filename, String charset) {
		StringBuilder text = new StringBuilder();

		Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(filename), charset);
			try {
				while (scanner.hasNextLine()) {
					text.append(scanner.nextLine());
				}
			} finally {
				scanner.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("marshalled " + text);
		
		return text.toString();
	}

}
