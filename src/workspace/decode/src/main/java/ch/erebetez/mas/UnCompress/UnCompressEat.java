/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
    Library to decode, encode in different Formats, like gzip, zlib.
    Input and output is as base64 String
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

	public UnCompressEat(String payload) {
		super(payload, "windows-1252");
	}

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
            System.out.println("Delimitterposition: " + delimiterPos);
			e1.printStackTrace();
		}

		checkUncompressionStringLength(unCompressedLenght, returnValue.length());

		return returnValue;
	}

	private int getDelimiterPosition(String compressedString) {

		return compressedString.indexOf(":");
	}

	private int getUnCompressedLength(String compressedString, int delimiterPos) {
		String theHexLength = compressedString.substring(0, delimiterPos);
		return Integer.valueOf(theHexLength, 16).intValue();
	}

	private void checkUncompressionStringLength(int expected, int actual) {
		if (expected != actual) {
			throw new IllegalArgumentException(
					"The uncompressed data is corrupt.");
		}
	}

}
