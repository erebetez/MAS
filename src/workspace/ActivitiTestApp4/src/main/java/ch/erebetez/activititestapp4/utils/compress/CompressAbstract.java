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

package ch.erebetez.activititestapp4.utils.compress;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


import org.apache.commons.codec.binary.Base64;

public abstract class CompressAbstract implements Compress {
	byte[] compressedData = null;
	String charset = null;
	String payload = null;

	public byte[] getCompressedData() {
		return compressedData;
	}
	
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public CompressAbstract(String payload) {		
		this(payload, "UTF-8");
	}

	public CompressAbstract(String payload, String charset) {
		super();
		this.charset = charset;
		this.payload = payload;
	}
	
	public String encodeDataToBase64(){
		// The URLSave mode does truncate the equals from the end.
		Base64 base64Object = new Base64(-1, null, false);		

		String base64String = new String(base64Object.encodeAsString( this.compressedData ));
		
		// It's easyer to replace the not URL conform Chars, than to calculate the needed equals to append.  
		base64String = base64String.replace('+', '-');
		base64String = base64String.replace('/', '_');
		
		return base64String;
	}
	
	public void saveOutputStreamAsByteArray(OutputStream outputStream){
		this.compressedData = ((ByteArrayOutputStream) outputStream).toByteArray();
	}
	
}
