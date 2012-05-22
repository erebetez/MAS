package ch.erebetez.marshall;

import java.text.DecimalFormat;
import java.util.*;

public class EatMarshaller {

	private final String cstrXmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

	private StringBuffer smlStringBuffer = new StringBuffer();


	public String marshall(Object varSource) {
		final String cstrTagEnvelop = cstrXmlDeclaration
				+ "<marshallenvelop major=\"%s\" minor=\"%s\" >%s</marshallenvelop>";
		final int clngMajor = 1;
		final int clngMinor = 1;
		String strMarshallXml;

		strMarshallXml = marshallDispatch(varSource);
		
		
		DecimalFormat df =   new DecimalFormat  ( "00" );
	    System.out.println(   df.format(clngMajor)   );           // 12.345,68
	    
		strMarshallXml = String.format(cstrTagEnvelop, df.format(clngMajor), df.format(clngMinor), strMarshallXml);

		// marshall = parseXml(strMarshallXml)

		return strMarshallXml;
	}

	private String marshallArray(List<Object> varArray) {
		final String cstrTagItem = "<item>%s</item>";
		final String cstrTagContainer = "<list>%s</list>";

		String strValue;
		StringBuffer stringBuffer = new StringBuffer();
		// Dim lngVarType As VbVarType

		if (varArray.size() >= 0) {

			for (int i = 0; i < varArray.size(); ++i) {
				strValue = marshallDispatch(varArray.get(i));

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
		// Dim lngVarType As VbVarType

		keysSet = varDictionary.keySet();

		if (varDictionary.size() > 0) {
			// ReDim strBuffer(LBound(varKeys) To UBound(varKeys))

			for (String key : keysSet) {
				// lngVarType = VarType(varDictionary.item(varKeys(lngCnt)))

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
		String strType = "string";
		String strLexical;

		// Lexikalische Form ermitteln
		// strLexical = canonical2Lexical_H(varValue);

		// TODO ....
		strLexical = (String) varValue;

		// Nacharbeitung für XML
		// switch (varValue) {
		// Case vbBoolean
		// strType = "boolean"
		// Case vbByte, vbInteger, vbLong
		// strType = "integer"
		// Case vbSingle, vbDouble, vbDecimal
		// strType = "decimal"
		// Case vbString
		// strType = "string"
		//
		// Dim strTmp As String
		// strTmp = encodeXmlEntinities_H(strLexical)
		// // Vorkommnisse von ]]> werden zu ]]]]><!CDATA[> übersetzt
		// strLexical = "<![CDATA[" & replace(strLexical, "]]>",
		// "]]]]><![CDATA[>") & "]]>"
		//
		// ' kürzere Variante wählen
		// if Len(strLexical) > Len(strTmp) { strLexical = strTmp
		// Case vbDate
		// strType = "dateTime"
		// Case vbNull, vbEmpty
		// strType = "string"
		// default:
		// strType = "string"
		// }

		return String.format(cstrTag, strType, strLexical);
	}

	private String marshallDispatch(Object varSource) {

		String className = varSource.getClass().getName();

		System.out.println("dispatch: class name :" + className);

		if (className == "java.util.HashMap") {

			return marshallDictionary((Map<String, Object>) varSource);
		} else if (className == "java.util.Vector") {

			return marshallArray((List<Object>) varSource);

		} else if (className == "java.lang.String") {

			return marshallAtom(varSource);
		} else {

			throw new IllegalArgumentException("Objekte des Types '"
					+ className + "' können nicht serialisiert werden!");

		}
	}
}
