package ch.erebetez.mas.UnCompress;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.Test;

import ch.erebetez.mas.UnCompress.UnCompressEat;
import ch.erebetez.mas.UnCompress.UnCompressGzip;
import ch.erebetez.mas.UnCompress.UnCompressInterface;

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
			e.printStackTrace();
		}

//		System.out.println("Text read in: " + text);

		return text.toString();
	}

	@Test
	public void uncompressGZip() throws IOException {

		UnCompressInterface comp = new UnCompressGzip(readFile("resources/myBaseZip.txt"));

		String xml = comp.uncompress();

		Assert.assertEquals("Hello", xml);

	}

	
	@Test
	public void uncomprssEatXmlBig() throws IOException {
		
		UnCompressInterface comp = new UnCompressEat(readFile("resources/biggerXml.txt"));
		
		String xml = comp.uncompress();

        String refxml = readFile("resources/biggerXml.xml");
		
		Assert.assertEquals(refxml, xml);

	}	

	@Test
	public void uncomprssEatXmlSmall() throws IOException {
		
		UnCompressInterface comp = new UnCompressEat(readFile("resources/shorterXml.txt"));
		
		String xml = comp.uncompress();

        String refxml = readFile("resources/shorterXml.xml");
		
		Assert.assertEquals(refxml, xml);

	}	
}
