package ch.erebetez.mas.UnCompress;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.InflaterInputStream;
import com.jcraft.jzlib.JZlib;

public class UnCompressEat extends UnCompressAbstract {

	UnCompressEat(String payload) {
		super(payload, "windows-1252");
	}

	@Override
	public String uncompress() {
		String returnValue = null;
		String compressedString;
		
		try {
			compressedString = new String(compressedData, super.charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int delimiterPos = getDelimiterPosition(compressedString);

		int unCompressedLenght = getUnCompressedLength(compressedString,
				delimiterPos);

		try {

			// FIXME: No idea why the position has to be shifted 3 bytes.
			byte[] source = Arrays.copyOfRange(compressedData,
					delimiterPos + 3, compressedData.length);

			InputStream inputStream = new InflaterInputStream(new ByteArrayInputStream(
					source), new Inflater(JZlib.DEF_WBITS, true));

			returnValue = super.readInputStream(inputStream);

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		checkUncompressionStringLength(unCompressedLenght, returnValue.length());

		return returnValue;
	}

	private int getDelimiterPosition(String compressedString) {

		return compressedString.indexOf(":");
	}

	private int getUnCompressedLength(String compressedString2, int delimiterPos) {
		String theHexLength = compressedString2.substring(0, delimiterPos);
		return Integer.valueOf(theHexLength, 16).intValue();
	}

	private void checkUncompressionStringLength(int expected, int actual) {
		if (expected != actual) {
			throw new IllegalArgumentException(
					"The uncompressed data is corrupt.");
		}
	}

}
