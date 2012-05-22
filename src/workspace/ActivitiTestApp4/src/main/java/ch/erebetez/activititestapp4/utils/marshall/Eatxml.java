package ch.erebetez.activititestapp4.utils.marshall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Eatxml {
	private String xml;
	
	//doc
	private static DocumentBuilder builder = null;
	private Document doc = null;

	
	public void setXml(String xml) throws SAXException, IOException {
		this.xml = xml;		
		
		setDocument();		
	}
	
	public Document getDoc() {
		return this.doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
		
//        // Prepare the output file
//        File file = new File(filename);
//        Result result = new StreamResult(file);
//
//        // Write the DOM document to the file
//        Transformer xformer = TransformerFactory.newInstance().newTransformer();
//        xformer.transform(source, result);
	}	
	
	private void setDocument() throws SAXException, IOException {
		setDoc(builder.parse(new InputSource(new StringReader(this.xml))));
	}
	
	
	public String getXml(){
		return this.xml;		
	}
	
	public Eatxml() throws ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		if(builder == null){
		    builder = factory.newDocumentBuilder();
		}
	}
	

	public boolean isValid() throws SAXException, IOException{

        // 1. Lookup a factory for the W3C XML Schema language
        SchemaFactory factory = 
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        
        // 2. Compile the schema. 
        // Here the schema is loaded from a java.io.File, but you could use 
        // a java.net.URL or a javax.xml.transform.Source instead.
        File schemaLocation = new File("MarshallSchema.xsd");
        Schema schema = factory.newSchema(schemaLocation);
    
        // 3. Get a validator from the schema.
        Validator validator = schema.newValidator();
        
        // 4. Parse the document you want to check.
        Source source = new StreamSource(new StringReader(this.xml));
        
        // 5. Check the document
        try {
            validator.validate(source);
            return true;
        }
        catch (SAXException ex) {           
            System.out.println(ex.getMessage());
            return false;
        }  
	}
	
	public boolean isLong() throws SAXException, IOException{
		return isValid();		
	}

	public void fromShortToLongXml() throws TransformerConfigurationException,
			FileNotFoundException, TransformerException, IOException {
		transform("xsltShortToLong.xslt");
	}

	public void fromLongToShortXml() throws TransformerConfigurationException,
			FileNotFoundException, TransformerException, IOException {
		transform("xsltLongToShort.xslt");
	}

	private void transform(String xslt) throws TransformerException,
			TransformerConfigurationException, FileNotFoundException,
			IOException {
		TransformerFactory tFactory = TransformerFactory.newInstance();

		Transformer transformer = tFactory
				.newTransformer(new StreamSource(new FileInputStream(xslt)));

		if (this.xml == null) {
			return;
		}

		Writer outWriter = new StringWriter();  
		StreamResult result = new StreamResult( outWriter ); 
		
		transformer.transform(new StreamSource(new StringReader(this.xml)), result);

		this.xml = outWriter.toString();		
	}



}
