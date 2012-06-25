package ch.erebetez.xmlutils;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

public class Eatxml {

	private static Logger log = Logger.getLogger(Eatxml.class.getName());

	private String xml;

    /**
 	 * @param xml an eat xml
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

    /**
 	 * @return an eat xml
	 */
	public String getXml() {
		return this.xml;
	}

    /**
     * Standard constructor
	 */
	public Eatxml() {
	}

    /**
     * Standard constructor
	 */	
	public Eatxml(String xml) {
		setXml(xml);
	}

    /**
     * Checks xml against the eat marshall xsd specification
 	 * @return true if xml is valid eat marshall xml.
	 */
	public boolean isValid() {
		try {
			validateXml();
			return true;
		} catch (SAXException ex) {
			log.log(Level.SEVERE, ex.getMessage());
			return false;
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
			return false;
		}
	}

	private void validateXml() throws SAXException, IOException {

		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");

		URL url = getClass().getClassLoader().getResource("MarshallSchema.xsd");
		Schema schema = factory.newSchema(url);

		Validator validator = schema.newValidator();

		Source source = new StreamSource(new StringReader(this.xml));

		validator.validate(source);
	}

    /**
     * 
 	 * @return true if xml is the long version
	 */
	public boolean isLong() {
		return isValid();
	}
	
		
    /**
     * 
 	 * @return Long xml
	 */
	public String fromShortToLongXml() throws TransformationError {
		transformXml("xsltShortToLong.xslt");
		return this.xml;
	}

	
    /**
     * @param shortXml the short xml
 	 * @return Long xml
	 */
	public String fromShortToLongXml(String shortXml) throws TransformationError {
		setXml(shortXml);
		return fromShortToLongXml();
	}	
	
    /**
     * 
 	 * @return short xml
	 */
	public String fromLongToShortXml() throws TransformationError {
		transformXml("xsltLongToShort.xslt");
		return this.xml;
	}
	
    /**
     * @param longXml the long xml
 	 * @return short xml
	 */
	public String fromLongToShortXml(String longXml) throws TransformationError {
		setXml(longXml);
		return fromLongToShortXml();
	}

	private void transformXml(String xsltPath) throws TransformationError {
		if (this.xml == null) {
			return;
		}
		
		try {
			transform(xsltPath);
		} catch (TransformerConfigurationException e) {
			throw new TransformationError(e);
		} catch (FileNotFoundException e) {
			throw new TransformationError(e);
		} catch (TransformerException e) {
			throw new TransformationError(e);
		} catch (IOException e) {
			throw new TransformationError(e);
		}
	}

	private void transform(String xsltPath) throws TransformerException,
			TransformerConfigurationException, FileNotFoundException,
			IOException {
		
		TransformerFactory tFactory = TransformerFactory.newInstance();

		URL url = getClass().getClassLoader().getResource(xsltPath);

		Transformer transformer = tFactory.newTransformer(new StreamSource(
				new InputStreamReader(url.openStream())));

		Writer outWriter = new StringWriter();
		StreamResult result = new StreamResult(outWriter);

		transformer.transform(new StreamSource(new StringReader(this.xml)),
				result);

		this.xml = outWriter.toString();
	}

}
