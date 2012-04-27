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

import java.io.*;
import java.util.zip.*;

public class CompressGzip extends CompressAbstract {

	CompressGzip(String payload) {
		super(payload);
	}

	@Override
	public String compress() {
		
		OutputStream outputStream = new ByteArrayOutputStream ();
		GZIPOutputStream outGzip = null;
		
		try {
			outGzip = new GZIPOutputStream( outputStream );
			
			outGzip.write(super.payload.getBytes(super.charset));
			outGzip.close();
			
			super.saveOutputStreamAsByteArray(outputStream);
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return super.encodeDataToBase64();

	}
	
	

}
