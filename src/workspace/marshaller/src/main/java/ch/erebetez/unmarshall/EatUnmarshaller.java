package ch.erebetez.unmarshall;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.*;
import org.xml.sax.*;


public class EatUnmarshaller {

	public enum tagNames{
	    list, 
	    dictionary, 
	    atom;
	}
	
	public enum VariableTypes{
		vTobject,
		vTarray,
		vTstring,
		vTinteger,
		vTdateTime,
		vTdecimal,
		vTboolean;
	}

	public Object unmarshall(Document docMarshall) throws XMLStreamException{
		
	    if(docMarshall == null){
	    	return null;
	    }
	     
	    Map<String, Object> dictionary = new HashMap<String, Object>();
	    
	    // TODO Check version
	    
	    docMarshall.normalize();
	    
	    Element element = docMarshall.getDocumentElement();

	    
	    
	    System.out.println("unmarshall start" + element.getNodeName());
	    
     	// TODO return long...
	    unmarshall01(element.getLastChild(), dictionary, 1);
	    		  

	    return dictionary;
	}
	
	private VariableTypes unmarshall01(Node docElement, Object varDestination, int lngMinor){

		
		if( docElement == null ){
			return null;
		}
		
		System.out.println(docElement.getLocalName());
		
	    String strNodeName = docElement.getNodeName();
        
        System.out.println( "Node:" + strNodeName );
        
        
        switch (tagNames.valueOf(strNodeName)){
            case list:
                varDestination = unmarshallArray01(docElement, lngMinor);
                return VariableTypes.vTarray;
            case dictionary:
                varDestination = unmarshallDictionary01(docElement, lngMinor);
                return VariableTypes.vTobject;
            case atom:
                varDestination = unmarshallAtom01(docElement, lngMinor);
                return VariableTypes.vTobject;
        }
        
        return null;
	}
	

	private Object unmarshallArray01(Node docElement, int lngMinor ){
		switch (lngMinor){
		case 1:
		    List<Object> tmpList = new Vector<Object>();
		    Object varDestination = new Object();		    
		    
	        if( docElement.getChildNodes().getLength() == 0 ){
	            return tmpList;
	        }
	        
	        NodeList list = docElement.getChildNodes();
	        
	        for( int index = 0; index < list.getLength(); ++index){
	            unmarshall01(list.item(index).getFirstChild(), varDestination, lngMinor);
	            tmpList.add(varDestination);
	        }

		    return tmpList;

		    
		default: 
			throw new IllegalArgumentException(
					"Minor Version " + lngMinor + " nicht unterstützt!");
	    }
	}
	

	private Object unmarshallDictionary01(Node docElement, int lngMinor ){
		switch (lngMinor){
		case 1:

			Object varDestination = new Object();
		    
			Map<String, Object> dict = new HashMap<String, Object>();
		    
			System.out.println("unmarshallDictionary01 length " + docElement.getChildNodes().getLength());
			
	        if( docElement.getChildNodes().getLength() == 0 ){
	            return dict;
	        }

			NodeList list = docElement.getChildNodes();
		    
	        for( int index = 0; index < list.getLength(); ++index ){
	        	System.out.println(list);
	        	
	        	// last child?
	            unmarshall01(list.item(index).getLastChild(), varDestination, lngMinor);
	            
	            String key = (String) unmarshallAtom01(list.item(index).getFirstChild(), lngMinor);
                dict.put(key, varDestination);                		
	        }

		    return dict;
	        
		default: 
			throw new IllegalArgumentException(
					"Minor Version " + lngMinor + " nicht unterstützt!");
	    }
	}




	private Object unmarshallAtom01(Node docElement, int lngMinor ){
		
		if(docElement == null){
			return null;
		}
		
		switch (lngMinor){
		case 1:

		    long lngVarType;
		    
			
		    NamedNodeMap attrib = docElement.getAttributes();
		    
		    System.out.println(attrib.getNamedItem("type").getNodeValue());
		    
		    
//		    switch (attrib.getNamedItem("type").getNodeValue()){
//		        case "boolean":
//		            lngVarType = vbBoolean
//		        case "integer":
//		            lngVarType = vbLong
//		        case "decimal":
//		            lngVarType = vbDecimal
//		        case "string":
//		            lngVarType = vbString
//		        case "dateTime":
//		            lngVarType = vbDate
//		    }
		
		    System.out.println(docElement.getNodeValue());
		    
//		    return pK009_lexical2Canonical_H(docElement.getNodeValue(), lngVarType);
		    return docElement.getNodeValue();
		    
		default: 
			throw new IllegalArgumentException(
					"Minor Version " + lngMinor + " nicht unterstützt!");
	    }
	}

}


//public static Document getDocument(String file) throws Exception {
//    
//    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
//    DocumentBuilder db = dbf.newDocumentBuilder();
//
//    Document doc = db.parse(new File(file));
//    return doc;
//}
//  
//public static void start( String[] argv )
//{
//if( argv.length != 2 )
//{
//  System.err.println( "Usage:   java ExampleDomShowNodes <XmlFile> <TagName>" );
//  System.err.println( "Example: java ExampleDomShowNodes MyXmlFile.xml Button" );
//  System.exit( 1 );
//}
//try {
//  // ---- Parse XML file ----
//  DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
//  DocumentBuilder        builder  = factory.newDocumentBuilder();
//  Document               document = builder.parse( new File( argv[0] ) );
//  // ---- Get list of nodes to given element tag name ----
//  NodeList ndList = document.getElementsByTagName( argv[1] );
//  printNodesFromList( ndList );  // printNodesFromList see below
//  // ---- Error handling ----
//} catch( SAXParseException spe ) {
//    System.out.println( "\n** Parsing error, line " + spe.getLineNumber()
//                                        + ", uri "  + spe.getSystemId() );
//    System.out.println( "   " + spe.getMessage() );
//    Exception e = ( spe.getException() != null ) ? spe.getException() : spe;
//    e.printStackTrace();
//} catch( SAXException sxe ) {
//    Exception e = ( sxe.getException() != null ) ? sxe.getException() : sxe;
//    e.printStackTrace();
//} catch( ParserConfigurationException pce ) {
//    pce.printStackTrace();
//} catch( IOException ioe ) {
//    ioe.printStackTrace();
//}
//}
//
//// ---- Helper methods ----
//
//private static void printObjIfVisible( String sValName, Object obj )
//{
//if( null == obj )  return;
//String s = obj.toString();
//if( null != s && 0 < s.trim().length() && !s.trim().equals( "\n" ) )
//  System.out.println( sValName + s );
//}
//
//public static void printNodeInfos( String sNodeName, Node node )
//{
//System.out.println(  "\n---------------------- " + sNodeName );
//if( null != node )
//{
//  printObjIfVisible(   "getNodeType()        = ", "" + node.getNodeType() );
//  printObjIfVisible(   "getNodeName()        = ", node.getNodeName() );
//  printObjIfVisible(   "getLocalName()       = ", node.getLocalName() );
//  printObjIfVisible(   "getNodeValue()       = ", node.getNodeValue() );
//  if( node.hasAttributes() )
//    printObjIfVisible( "getAttributes()      = ", node.getAttributes() );
//  if( node.hasChildNodes() ) {
//    printObjIfVisible( "getChildNodes()      = ", node.getChildNodes() );
//    printObjIfVisible( "getFirstChild()      = ", node.getFirstChild() );
//  }
//  printObjIfVisible(   "getPreviousSibling() = ", node.getPreviousSibling() );
//  printObjIfVisible(   "getNextSibling()     = ", node.getNextSibling() );
//}
//System.out.println(    "----------------------\n" );
//}
//
//public static void printNodesFromList( NodeList ndList )
//{
//for( int i=0; i<ndList.getLength(); i++ )
//  printNodeInfos( "ndList.item("+i+")", ndList.item(i) );
//}