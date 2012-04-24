package ch.erebetez.marshall;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {

	private static final String XML_FILE_NAME = "sequence.xml";

	@Test
	public void testApp() throws IOException {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"marshaller-context.xml");
		XMLConverter converter = (XMLConverter) appContext
				.getBean("XMLConverter");

		Sequence sequence = new Sequence();
		sequence.setName("mkyong");
		sequence.setLenght(30);
		sequence.setFlag(true);
		sequence.setMethod("MID00001");

		List<String> data = Arrays.asList("abc", "de", "f", "g");

		sequence.setRawdata(data);

		Map<String, String> map = new HashMap<String, String>();

		map.put("name", "cool");
		map.put("givenname", "otto");

		sequence.setDictionary(map);

		List<Assay> assayList = new Vector<Assay>();

		for (int i = 0; i < 3; ++i) {
			assayList.add(new Assay());
			assayList.get(i).setId(Integer.toString(i));
			assayList.get(i).setToken("hello01");

			Map<String, String> results = new HashMap<String, String>();

			results.put("weight", "0.5");
			results.put("peak", "2000");

			assayList.get(i).setResults(results);

		}

		sequence.setAssayList(assayList);

		System.out.println("Convert Object to XML!");
		// from object to XML file
		converter.convertFromObjectToXML(sequence, XML_FILE_NAME);
		System.out.println("Done \n");

		System.out.println("Convert XML back to Object!");
		// from XML to object
		Sequence sequence2 = (Sequence) converter
				.convertFromXMLToObject(XML_FILE_NAME);
		System.out.println(sequence2);
		System.out.println("Done");
	}
}
