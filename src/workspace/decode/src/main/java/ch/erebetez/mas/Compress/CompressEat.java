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

package ch.erebetez.mas.Compress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.JZlib;

public class CompressEat extends CompressAbstract {

	public CompressEat(String payload) {
		super(payload, "windows-1252");
	}

	public String compress() {

		OutputStream outputStream = new ByteArrayOutputStream();
		DeflaterOutputStream outZlib = null;

		try {
			outZlib = new DeflaterOutputStream(outputStream,  new Deflater(JZlib.Z_BEST_COMPRESSION) );

			outZlib.write(super.payload.getBytes(super.charset));
			outZlib.close();

			super.saveOutputStreamAsByteArray(outputStream);

			prependLenghtInformation();

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return super.encodeDataToBase64();

	}

	private void prependLenghtInformation() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(Integer.toHexString(super.payload.length()));
		buffer.append(":");

		buffer.toString().getBytes();

		super.compressedData = concatByte(buffer.toString().getBytes(),
				super.compressedData);
		

	}

	private byte[] concatByte(byte[] first, byte[] second) {
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}
