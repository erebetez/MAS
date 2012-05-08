package ch.erebetez.mas.UnCompress;

import java.io.IOException;

import ch.erebetez.utils.*;

import junit.framework.Assert;

import org.junit.Test;

import ch.erebetez.mas.UnCompress.UnCompressEat;
import ch.erebetez.mas.UnCompress.UnCompressGzip;
import ch.erebetez.mas.UnCompress.UnCompress;

public class UnCompressTest {

	@Test
	public void uncompressGZip() throws IOException {

		String compressedData = FileWriters.readFile("resources/myBaseZip.txt");
		
		UnCompress unComp = new UnCompressGzip(compressedData);
		Assert.assertEquals("Hello", unComp.uncompress());

	}

	
	@Test
	public void uncomprssEatXmlBig() throws IOException {
		
		UnCompress comp = new UnCompressEat(FileWriters.readFile("resources/biggerXml.txt"));
		
		
		String xml = comp.uncompress();

        String refxml = FileWriters.readFile("resources/biggerXml.xml", "windows-1252");
		
		Assert.assertEquals(refxml, xml);

	}	

	@Test
	public void uncomprssEatXmlSmall() throws IOException {
		
		UnCompress comp = new UnCompressEat(FileWriters.readFile("resources/shorterXml.txt"));
		
		String xml = comp.uncompress();

        String refxml = FileWriters.readFile("resources/shorterXml.xml");
		
		Assert.assertEquals(refxml, xml);

	}	
	
	@Test
	public void uncomprssEatXmlHalloSmall() throws IOException {
		
		UnCompress comp = new UnCompressEat(FileWriters.readFile("resources/HalloString.txt"));
		
//		FileWriters.writeByteToFile(comp.getCompressedData());
		
		Assert.assertEquals("Hallo", comp.uncompress());

	}	
}
