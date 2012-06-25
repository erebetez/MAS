package ch.erebetez.marshall.tests;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import junit.framework.Assert;

import org.junit.Test;

import ch.erebetez.marshall.utils.FileWriters;
import ch.erebetez.marshalling.EatMarshaller;
import ch.erebetez.marshalling.EatUnmarshaller;
import ch.erebetez.xmlutils.Eatxml;
import ch.erebetez.xmlutils.TransformationError;

public class MarshallTest {

	@Test
	public void convertLongToShort() {
		Eatxml xml = new Eatxml();

		xml.setXml(FileWriters.readFile("src/test/resources/long.xml"));

		try {
			xml.fromLongToShortXml();
		} catch (TransformationError e) {
			e.printStackTrace();
			Assert.fail();
		}

		String ref = "<E v=\"1.1\"><D><M><s>K010#META01</s><D><M>";

		Assert.assertEquals(ref, xml.getXml().substring(0, ref.length()));
	}

	@Test
	public void convertShortToLong() {
		Eatxml xml = new Eatxml();

		try {

			xml.setXml(FileWriters.readFile("src/test/resources/short.xml"));

			xml.fromShortToLongXml();

			Assert.assertTrue(xml.isValid());
			
		} catch (TransformationError e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void unMarshallTest(){

		Eatxml xml = new Eatxml();
		EatUnmarshaller umarsh = new EatUnmarshaller();

		HashMap<String, Object> dict = null;

		xml.setXml(FileWriters.readFile("src/test/resources/long.xml"));

		dict = (HashMap<String, Object>) umarsh.unmarshall(xml.getXml());

		Assert.assertNotNull(dict.get("K010#META01"));

		// dateTime 2012-03-28T00:00:00.000

	}

	@Test
	public void DictionaryTest() {

		Map<String, Object> dict = new HashMap<String, Object>();

		dict.put("myString", "Hi");

		Map<String, Object> dict2 = new HashMap<String, Object>();
		List<Object> listInDict = new Vector<Object>();

		listInDict.add("1");
		listInDict.add("2");
		listInDict.add("3");

		dict2.put("Hallo", "bla");
		dict2.put("Resulst", listInDict);
		dict2.put("bool", true);
		dict2.put("integer", 12354);
		dict2.put("double", 12.33);
		dict2.put("date", Calendar.getInstance().getTime());

		List<Object> list = new Vector<Object>();

		list.add("ListString");
		list.add("Test abc.");

		dict.put("list01", list);
		dict.put("dict2", dict2);

		List<Object> assays = new Vector<Object>();

		assays.add(dict);
		assays.add("Test abc.");

		// System.out.println(assays);

		EatMarshaller marsh = new EatMarshaller();
		String xml = marsh.marshall(assays);

		// System.out.println(xml);

		Eatxml eatXml = new Eatxml();


		eatXml.setXml(xml);
		Assert.assertTrue(eatXml.isValid());


		EatUnmarshaller unMarshaller = new EatUnmarshaller(xml);

		Assert.assertTrue(unMarshaller.getEatObject().equals(assays));

	}

	@Test
	public void EmptyMarshallObject() {
		EatMarshaller marsh = new EatMarshaller();
		String xml = marsh.marshall(null);

		Assert.assertEquals("", xml);
	}
}
