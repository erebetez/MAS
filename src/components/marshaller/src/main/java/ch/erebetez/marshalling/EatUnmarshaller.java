/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
    Library to marshall, unmarshall in the eat objects
    Copyright (C) 2012  Etienne Rebetez

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */

package ch.erebetez.marshalling;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ch.erebetez.xmlobjects.*;
import ch.erebetez.xmlobjects.CtDictionary.Member;
import ch.erebetez.xmlobjects.CtList.Item;

/**
 * Class to Unmarshall xml that follow the Eat spesification.
 * 
 * @author      Panos Stergiotis 
 * @author      Etienne Rebetez
 * @version     %I%, %G%
 * @since       1.0
 */
public class EatUnmarshaller {

	private final static int MAJORVERSION = 1;
	private final static int MINORVERSION = 1;

	private static Logger log = Logger.getLogger(EatUnmarshaller.class
			.getName());
	
	private Object eatObject = null;

	/**
	 * Returns the unmarshalled Object.
	 * Either EatUnmarshaller(String marshalledString)
	 * or unmarshall(String marshalledString) have to
	 * have been executed
	 *
	 * @return    the unmarshalled Eat Object
	 */
	public Object getEatObject() {
		if (eatObject == null) {
			eatObject = new HashMap<String, Object>();
		}
		return eatObject;
	}

	/**
     * Basic Constructor
     * unmarshall(String marshalledString) has to be called.
	 */
	public EatUnmarshaller() {
	}

	/**
     * Overloaded Constructor
     * calls directly unmarshall(String marshalledString)
     * @param marshalledString The marshalled String to unmarshall
	 */
	public EatUnmarshaller(String marshalledString){
		unmarshall(marshalledString);
	}

	/**
	 * Returns the unmarshalled Object.
	 * Either EatUnmarshaller(String marshalledString)
	 * or unmarshall(String marshalledString) have to
	 * have been executetd
	 *
	 * @param marshalledString The marshalled String to unmarshall
	 * @return    the unmarshalled Eat Object
	 */
	public Object unmarshall(String marshalledString) {

		try {
			runUnmarshall(marshalledString);
		} catch (JAXBException e) {
			log.log(Level.SEVERE, e.getMessage());
		}

		return getEatObject();
	}

	private void runUnmarshall(String marshalledString) throws JAXBException {

		if (marshalledString == null) {
			throw new IllegalArgumentException("No parameter was given");
		}

		JAXBContext jc = JAXBContext.newInstance("ch.erebetez.xmlobjects");

		Unmarshaller u = jc.createUnmarshaller();

		Marshallenvelop marshallEnv = (Marshallenvelop) u
				.unmarshal(new StringReader(marshalledString));

		checkVersion(marshallEnv);

		setEatObject(unmarshall01(getRootElement(marshallEnv)));
	}
	

	
	private void checkVersion(Marshallenvelop marshallEnv){
		if (marshallEnv.getMajor() != MAJORVERSION) {
			throw new IllegalArgumentException("Major Version "
					+ marshallEnv.getMajor() + " not supported!");
		}

		if (marshallEnv.getMinor() != MINORVERSION) {
			throw new IllegalArgumentException("Minor Version "
					+ marshallEnv.getMinor() + " not supported!");
		}
	}

	private Object getRootElement(Marshallenvelop marshallEnv){
		if(marshallEnv.getDictionary() != null){
			return marshallEnv.getDictionary();
		}
		
		if(marshallEnv.getList() != null){
			return marshallEnv.getList();
		}
		
		if(marshallEnv.getAtom() != null){
			return marshallEnv.getAtom();
		}
		
		throw new IllegalArgumentException("Marshallenvelop object has no root");
	}

	private void setEatObject(Object eatObject) {
		this.eatObject = eatObject;
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

		throw new IllegalArgumentException("No match for node type "
				+ docElement.getClass());
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

			// getAtomOrDictionaryOrList has always one item.
			Object returnValue = unmarshall01(list.get(index)
					.getAtomOrDictionaryOrList().get(0));

			tmpList.add(returnValue);
		}

		return tmpList;

	}

	private Object unmarshallAtom01(CtAtom atom) {

		switch (atom.getType()) {

		case STRING:
			return (String) atom.getValue();

		case DECIMAL:
			return Double.parseDouble(atom.getValue());

		case INTEGER:
			return Integer.parseInt(atom.getValue());

		case BOOLEAN:
			return Boolean.parseBoolean(atom.getValue());

		case DATE_TIME:
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			
			Date date = null;
			try {
				date = dateformat.parse(atom.getValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;

		}
		return null;
	}

}
