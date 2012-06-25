package ch.erebetez.decode.tests;

import java.io.IOException;


import junit.framework.Assert;

import org.junit.Test;

import ch.erebetez.decode.UnCompress.UnCompress;
import ch.erebetez.decode.UnCompress.UnCompressEat;
import ch.erebetez.decode.UnCompress.UnCompressGzip;
import ch.erebetez.decode.utils.*;

public class UnCompressTest {

	@Test
	public void uncompressGZip() throws IOException {
		
		String compressedData = FileWriters.readFile(getClass().getClassLoader().getResource("myBaseZip.txt"));
		
		UnCompress unComp = new UnCompressGzip(compressedData);
		Assert.assertEquals("Hello", unComp.uncompress());

	}

	
	@Test
	public void uncomprssEatXmlBig() throws IOException {
		
		UnCompress comp = new UnCompressEat(FileWriters.readFile(getClass().getClassLoader().getResource("biggerXml.txt")));
		
		
		String xml = comp.uncompress();
		
        String refxml = FileWriters.readFile(getClass().getClassLoader().getResource("biggerXml.xml"), "windows-1252");
		
		Assert.assertEquals(refxml, xml);

	}	

	@Test
	public void uncomprssEatXmlSmall() throws IOException {

		UnCompress comp = new UnCompressEat(FileWriters.readFile(getClass().getClassLoader().getResource("shorterXml.txt")));
		
		String xml = comp.uncompress();
		
        String refxml = FileWriters.readFile(getClass().getClassLoader().getResource("shorterXml.xml"));
		
		Assert.assertEquals(refxml, xml);

	}	
	
	@Test
	public void uncomprssEatXmlHalloSmall() throws IOException {
		
		UnCompress comp = new UnCompressEat(FileWriters.readFile(getClass().getClassLoader().getResource("HalloString.txt")));
		
//		FileWriters.writeByteToFile(comp.getCompressedData());
		
		Assert.assertEquals("Hallo", comp.uncompress());

	}
	
	@Test
	public void uncomprssEatXmlDict() throws IOException {
		
		UnCompress comp = new UnCompressEat(FileWriters.readFile(getClass().getClassLoader().getResource("dict.txt")));
		
//		System.out.println(comp.uncompress());
		
		Assert.assertEquals("<E v=\"1.1\"><D><M><s>location</s><s>B02R005</s></M><M><s>oosNumber</s><i>0</i></M><M><s>lot</s><s>122234</s></M><M><s>isItemOk</s><b>true</b></M></D></E>", comp.uncompress());

	}	
}
