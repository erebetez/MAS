package ch.erebetez.unmarshall;

import java.math.BigInteger;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.*;


import ch.erebetez.xmlobjects.*;
import ch.erebetez.xmlobjects.CtDictionary.Member;
import ch.erebetez.xmlobjects.CtList.Item;


public class EatUnmarshaller {

	
	
	public enum VariableTypes{
		vTobject,
		vTarray,
		vTstring, 
		vTinteger,
		vTdateTime,
		vTdecimal,
		vTboolean;
	}

	public Object unmarshall(Document docMarshall) {
		
	    if(docMarshall == null){
	    	return null;
	    }
	     
	    Map<String, Object> dictionary = new HashMap<String, Object>();
	    
	    // TODO Check version
	    
	    JAXBContext jc;
	    Unmarshaller u;
	    Marshallenvelop marshallEnv = null;
	    
		try {
			jc = JAXBContext.newInstance( "ch.erebetez.xmlobjects" );
			
			u = jc.createUnmarshaller();
			
			marshallEnv = (Marshallenvelop) u.unmarshal( docMarshall );
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		if(marshallEnv == null){
			return dictionary;
		}
	    

	    
	    System.out.println("Major " + marshallEnv.getMajor());

	    System.out.println("Minor " + marshallEnv.getMinor());

	    
	    // FIXME it is not always a dictionara as root.
	    CtDictionary firstDict = marshallEnv.getDictionary();
	    

	    dictionary = (Map<String, Object>) unmarshall01(firstDict, 1);

	    return dictionary;
	}
	
	
	private Object unmarshall01(Object docElement, int lngMinor){

// FIXME remove?		
		if( docElement == null ){
			return null;
		}
		
		System.out.println("unmarshall node type " + docElement.getClass());		
        
        
        if( docElement.getClass().getName().equals("ch.erebetez.xmlobjects.CtDictionary")){
        	return unmarshallDictionary01((CtDictionary) docElement, lngMinor);
        }
        
        if( docElement.getClass().getName().equals("ch.erebetez.xmlobjects.CtList")){
        	return unmarshallArray01((CtList) docElement, lngMinor);
        }
        
        if( docElement.getClass().getName().equals("ch.erebetez.xmlobjects.CtAtom")){
        	return unmarshallAtom01((CtAtom) docElement, lngMinor);
        }
        
        System.out.println("No match for node type " + docElement.getClass());
        
        return null;
	}
	


	private Object unmarshallDictionary01(CtDictionary docElement, int lngMinor ){
		switch (lngMinor){
		case 1:

			
			Object varDestination = new Object();
		    
			Map<String, Object> dict = new HashMap<String, Object>();
		    
			System.out.println("unmarshallDictionary01 with members");
			
			List<Member> memberList = docElement.getMember(); 

	        for( int index = 0; index < memberList.size(); ++index ){
	        	System.out.println("dict Item " + index);
	        	
	        	List<Object> member = memberList.get(index).getAtomOrDictionaryOrList();
	        	
	            varDestination = unmarshall01(member.get(1) , lngMinor);
	            
	            String key = (String) unmarshallAtom01((CtAtom) member.get(0), lngMinor);
                dict.put(key, varDestination);                		
	        }

		    return dict;
	        
		default: 
			throw new IllegalArgumentException(
					"Minor Version " + lngMinor + " not supported!");
	    }
	}
	
	
	private Object unmarshallArray01(CtList docElement, int lngMinor ){
		switch (lngMinor){
		case 1:

			
		    List<Object> tmpList = new Vector<Object>();
		    Object varDestination = new Object();
		    
		    
	        List<Item> list = docElement.getItem();
	        
	        for( int index = 0; index < list.size(); ++index){
	        	
	        	// getAtomOrDictionaryOrList has one item. 
	        	varDestination = unmarshall01(list.get(index).getAtomOrDictionaryOrList().get(0), lngMinor);
	            tmpList.add(varDestination);
	            
	        }

		    return tmpList;

		    
		default: 
			throw new IllegalArgumentException(
					"Minor Version " + lngMinor + " not supported!");
	    }
	}
	





	private Object unmarshallAtom01(CtAtom atom, int lngMinor ){
		switch (lngMinor){
		case 1:

			if(atom == null){
				return null;
			}
			
			System.out.println("atom node value " + atom.getValue());	
			
			System.out.println("atom node type " + atom.getType());	
			
//		    NamedNodeMap attrib = docElement.getAttributes();
//		    
//		    System.out.println(attrib.getNamedItem("type").getNodeValue());
		    
//		    long lngVarType;
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
		
//		    System.out.println("node type " + list.item(0).getNodeType());
		    
//		    return pK009_lexical2Canonical_H(docElement.getNodeValue(), lngVarType);
		    return atom.getValue();
		    
		default: 
			throw new IllegalArgumentException(
					"Minor Version " + lngMinor + " not supported!");
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