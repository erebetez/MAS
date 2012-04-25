package ch.erebetez.mas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.Test;

public class UnCompressTest {

	private String readFile(String filename) {
		StringBuilder text = new StringBuilder();

		Scanner scanner;
		try {
			scanner = new Scanner(
					new FileInputStream(filename));
			try {
				while (scanner.hasNextLine()) {
					text.append(scanner.nextLine());
				}
			} finally {
				scanner.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("Text read in: " + text);

		return text.toString();
	}

//	@Test
//	public void uncomprss() throws IOException {
//
//		UnCompress comp = new UnCompress(readFile("resources/myBaseZip.txt"));
//
//		String xml = comp.getValue();
//
//		System.out.println(xml);
//
//		Assert.assertEquals("Hello", xml);
//
//	}
//
//	@Test
//	public void uncomprssNoZip() throws IOException {
//
//		UnCompress comp = new UnCompress(readFile("resources/myBaseNoZip.txt"));
//
//		String xml = comp.getValue();
//
//		System.out.println(xml);
//
//		Assert.assertEquals("Hello2", xml);
//
//	}
	
	@Test
	public void uncomprssEatXml() throws IOException {
		
		System.out.println(Charset.defaultCharset().toString());
		
		UnCompress comp = new UnCompress(readFile("resources/biggerXml.txt"), "windows-1252");
		
		String xml = comp.getValue();

        String refxml = readFile("resources/shortXml.xml");
		
		Assert.assertEquals(refxml, xml);

	}	

}
