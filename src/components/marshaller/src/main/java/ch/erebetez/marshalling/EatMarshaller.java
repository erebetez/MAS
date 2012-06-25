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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Class to Marshall eat Objects to xml.
 * 
 * 
 * @author      Panos Stergiotis 
 * @author      Etienne Rebetez
 * @version     %I%, %G%
 * @since       1.0
 */
public class EatMarshaller {

	private final String cstrXmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

    /**
     * 
     * @param dataObject the eat object to marshall
 	 * @return returns the marshalled xml string
	 */
	public String marshall(Object dataObject) {
		final String cstrTagEnvelop = cstrXmlDeclaration
				+ "<marshallenvelop major=\"%s\" minor=\"%s\" >%s</marshallenvelop>";
		final int clngMajor = 1;
		final int clngMinor = 1;
		String strMarshallXml = "";
		
		if(dataObject == null){
			return strMarshallXml;
		}
		
		strMarshallXml = marshallDispatch(dataObject);

		DecimalFormat df = new DecimalFormat("00");

		strMarshallXml = String.format(cstrTagEnvelop, df.format(clngMajor),
				df.format(clngMinor), strMarshallXml);

		return strMarshallXml;
	}

	private String marshallArray(List<Object> varArray) {
		final String cstrTagItem = "<item>%s</item>";
		final String cstrTagContainer = "<list>%s</list>";

		StringBuffer stringBuffer = new StringBuffer();

		if (varArray.size() >= 0) {

			for (int i = 0; i < varArray.size(); ++i) {
				String strValue = marshallDispatch(varArray.get(i));

				stringBuffer.append(String.format(cstrTagItem, strValue));
			}
		}

		return String.format(cstrTagContainer, stringBuffer.toString());
	}

	private String marshallDictionary(Map<String, Object> varDictionary) {
		final String cstrTagMember = "<member>%s%s</member>";
		final String cstrTagContainer = "<dictionary>%s</dictionary>";

		StringBuffer stringBuffer = new StringBuffer();
		Set<String> keysSet;
		String strKey;
		String strValue;

		keysSet = varDictionary.keySet();

		if (varDictionary.size() > 0) {

			for (String key : keysSet) {

				strKey = marshallDispatch(key);
				strValue = marshallDispatch(varDictionary.get(key));

				stringBuffer.append(String.format(cstrTagMember, strKey,
						strValue));
			}
		}

		return String.format(cstrTagContainer, stringBuffer.toString());
	}

	private String marshallAtom(Object varValue) {
		final String cstrTag = "<atom type=\"%s\">%s</atom>";
		String variableType = "string";
		String lexicalString = "";

		String className = varValue.getClass().getName();

		if (className.equals("java.lang.String")) {
			variableType = "string";
			lexicalString = (String) varValue;

		} else if (className.equals("java.lang.Integer")
				|| className.equals("java.lang.Long")) {
			variableType = "integer";
			lexicalString = varValue.toString();

		} else if (className.equals("java.lang.Double")) {
			variableType = "decimal";
			lexicalString = varValue.toString();

		} else if (className.equals("java.lang.Boolean")) {
			variableType = "boolean";
			lexicalString = (String) new Boolean((Boolean) varValue).toString();

		} else if (className.equals("java.util.Date")) {
			variableType = "dateTime";
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");			
			lexicalString = dateformat.format(varValue);

		} else {
			throw new IllegalArgumentException("objects of type '" + className
					+ "' can't be serialized!");
		}

		return String.format(cstrTag, variableType, lexicalString);
	}

	@SuppressWarnings("unchecked")
	private String marshallDispatch(Object varSource) {

		String className = varSource.getClass().getName();

		if (className.equals("java.util.HashMap") || className.equals("java.util.LinkedHashMap")) {
			return marshallDictionary((Map<String, Object>) varSource);

		} else if (className.equals("java.util.Vector") || className.equals("java.util.ArrayList")) {
			return marshallArray((List<Object>) varSource);

		} else {
			return marshallAtom(varSource);

		}
	}
}
