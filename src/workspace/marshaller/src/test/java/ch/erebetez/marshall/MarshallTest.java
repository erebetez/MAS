package ch.erebetez.marshall;

import java.io.IOException;
import java.util.*;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import ch.erebetez.unmarshall.EatUnmarshaller;
import ch.erebetez.utils.FileWriters;
import ch.erebetez.xmlutils.Eatxml;



public class MarshallTest {

	@Test
	public void convertLongToShort() throws IOException, ParserConfigurationException {
		Eatxml xml = new Eatxml();
		
		
		
		try {
			
			xml.setXml(FileWriters.readFile("resources/test/long.xml"));
			xml.fromLongToShortXml();
			
		} catch (TransformerConfigurationException e) {

			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ref = "<E v=\"1.1\"><D><M><s>K010#META01</s><D><M>";
		
		Assert.assertEquals(ref, xml.getXml().substring(0, ref.length()));
	}
	
	@Test
	public void convertShortToLong() throws IOException, ParserConfigurationException {
		Eatxml xml = new Eatxml();
		
		
		
		try {
			
			xml.setXml(FileWriters.readFile("resources/test/short.xml"));		
			
			xml.fromShortToLongXml();
		} catch (TransformerConfigurationException e) {

			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Assert.assertTrue(xml.isValid());
		} catch (SAXException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test	
	public void unMarshallTest() throws ParserConfigurationException{
		
		Eatxml xml = new Eatxml();
		EatUnmarshaller umarsh = new EatUnmarshaller();

		Object dict = null;
		
		try {
			
		    xml.setXml(FileWriters.readFile("resources/test/long.xml"));

		    
			dict = umarsh.unmarshall(xml.getDoc());
			

		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(dict);
		
	}
	
	
	
	@Test	
	public void DictionaryTest() throws ParserConfigurationException{
		
		Map<String, Object> dict = new HashMap<String, Object>();
		
		dict.put("myString", "Hi");
		
		Map<String, Object> dict2 = new HashMap<String, Object>();
		List<Object> listInDict = new Vector<Object>();
		
		listInDict.add("1");
		listInDict.add("2");
		listInDict.add("3");
		
		dict2.put("Hallo", "bla");
		dict2.put("Resulst", listInDict);
		
		
		List<Object> list = new Vector<Object>();
		
		list.add("ListString");
		list.add("Test abc.");
		
		dict.put("list01", list);
		dict.put("list03", list);
		dict.put("dict2", dict2);		
		
		
        List<Object> assays = new Vector<Object>();
		
        assays.add(dict);
        assays.add("Test abc.");
		

		System.out.println(dict);
		
		List<Object> returnList = (List<Object>) dict.get("list01");
		
		System.out.println(returnList.get(0));
		
		EatMarshaller marsh = new EatMarshaller();
		String xml = marsh.marshall(dict);
		
		System.out.println(xml);
		
		Eatxml eatXml = new Eatxml();
		
		try {
			eatXml.setXml(xml);
			Assert.assertTrue(eatXml.isValid());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		
		
		
	}	
	
	
	
}
