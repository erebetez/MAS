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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public abstract class UnCompressAbstract implements UnCompress {
	byte[] compressedData = null;
	String charset = null;

	public byte[] getCompressedData() {
		return compressedData;
	}
	
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	UnCompressAbstract(String payload) {		
		this(payload, "UTF-8");
	}

	UnCompressAbstract(String payload, String charset) {
		super();
		this.charset = charset;
		
		if(Base64.isBase64(payload)) {
			this.compressedData = Base64.decodeBase64(payload);
		} else {
			this.compressedData = payload.getBytes();
		}
	}
	
	public String readInputStream(InputStream inputStream) throws IOException {

		InputStreamReader reader = new InputStreamReader(inputStream,
				Charset.forName(charset));

		BufferedReader in = new BufferedReader(reader);

		StringBuffer readed = new StringBuffer();
		String read;

		while ((read = in.readLine()) != null) {
			readed.append(read);
		}
		
		return readed.toString();
	}
	
}
