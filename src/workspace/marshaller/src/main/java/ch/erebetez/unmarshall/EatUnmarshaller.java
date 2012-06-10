package ch.erebetez.unmarshall;

import java.io.StringReader;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


import ch.erebetez.xmlobjects.*;
import ch.erebetez.xmlobjects.CtDictionary.Member;
import ch.erebetez.xmlobjects.CtList.Item;

public class EatUnmarshaller {

	private final static int MAJORVERSION = 1;
	private final static int MINORVERSION = 1;

	public enum VariableTypes {
		vTobject, vTarray, vTstring, vTinteger, vTdateTime, vTdecimal, vTboolean;
	}

	private Map<String, Object> eatObject = null;

	public Map<String, Object> getEatObject() {
		if (eatObject == null) {
			eatObject = new HashMap<String, Object>();
		}
		return eatObject;
	}

	private void setEatObject(Map<String, Object> eatObject) {
		this.eatObject = eatObject;
	}

	public EatUnmarshaller() {
	}
	
	public EatUnmarshaller(String marshalledString) throws JAXBException {
		unmarshall(marshalledString);
	}

	public Object unmarshall(String marshalledString) throws JAXBException {

		runUnmarshall(marshalledString);

		return getEatObject();
	}

	@SuppressWarnings("unchecked")
	private void runUnmarshall(String marshalledString) throws JAXBException {

		if (marshalledString == null) {
			throw new IllegalArgumentException("No parameter was given");
		}

		JAXBContext jc = JAXBContext.newInstance("ch.erebetez.xmlobjects");

		Unmarshaller u = jc.createUnmarshaller();

		Marshallenvelop marshallEnv = (Marshallenvelop) u
				.unmarshal(new StringReader(marshalledString));

		if (marshallEnv.getMajor() != MAJORVERSION) {
			throw new IllegalArgumentException("Major Version "
					+ marshallEnv.getMajor() + " not supported!");
		}

		if (marshallEnv.getMinor() != MINORVERSION) {
			throw new IllegalArgumentException("Minor Version "
					+ marshallEnv.getMinor() + " not supported!");
		}

		// FIXME it is not always a dictionara as root.
		CtDictionary firstDict = marshallEnv.getDictionary();

		setEatObject((Map<String, Object>) unmarshall01(firstDict));

	}

	private Object unmarshall01(Object docElement) {

		if (docElement.getClass().getName()
				.equals("ch.erebetez.xmlobjects.CtDictionary")) {
			return unmarshallDictionary01((CtDictionary) docElement);
		}

		if (docElement.getClass().getName()
				.equals("ch.erebetez.xmlobjects.CtList")) {
			return unmarshallArray01((CtList) docElement);
		}

		if (docElement.getClass().getName()
				.equals("ch.erebetez.xmlobjects.CtAtom")) {
			return unmarshallAtom01((CtAtom) docElement);
		}

		throw new IllegalArgumentException("No match for node type " + docElement.getClass());
	}

	private Object unmarshallDictionary01(CtDictionary unMarshallDict) {

		Map<String, Object> dict = new HashMap<String, Object>();

		List<Member> memberList = unMarshallDict.getMember();

		for (int index = 0; index < memberList.size(); ++index) {

			List<Object> member = memberList.get(index)
					.getAtomOrDictionaryOrList();

			// The first member argument is the dictionary key.
			String key = (String) unmarshallAtom01((CtAtom) member.get(0));
						
			// The second member argument is the value
			Object returnValue = unmarshall01(member.get(1));

			dict.put(key, returnValue);
		}

		return dict;
	}

	private Object unmarshallArray01(CtList unMarshallList) {

		List<Object> tmpList = new Vector<Object>();

		List<Item> list = unMarshallList.getItem();

		for (int index = 0; index < list.size(); ++index) {

			// getAtomOrDictionaryOrList has one item.
			Object returnValue = unmarshall01(list.get(index)
					.getAtomOrDictionaryOrList().get(0));

			tmpList.add(returnValue);
		}

		return tmpList;

	}

	private Object unmarshallAtom01(CtAtom atom) {

		System.out.println("atom value " + atom.getValue());

		System.out.println("atom type " + atom.getType());

		// TODO
		
		// NamedNodeMap attrib = docElement.getAttributes();
		//
		// System.out.println(attrib.getNamedItem("type").getNodeValue());

		// long lngVarType;
		// switch (attrib.getNamedItem("type").getNodeValue()){
		// case "boolean":
		// lngVarType = vbBoolean
		// case "integer":
		// lngVarType = vbLong
		// case "decimal":
		// lngVarType = vbDecimal
		// case "string":
		// lngVarType = vbString
		// case "dateTime":
		// lngVarType = vbDate
		// }

		// System.out.println("node type " + list.item(0).getNodeType());

		// return pK009_lexical2Canonical_H(docElement.getNodeValue(),
		// lngVarType);
		return atom.getValue();

	}

}
