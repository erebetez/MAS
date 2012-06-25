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

package ch.erebetez.decode.tests;


import java.io.IOException;


import junit.framework.Assert;

import org.junit.Test;

import ch.erebetez.decode.Compress.Compress;
import ch.erebetez.decode.Compress.CompressEat;
import ch.erebetez.decode.Compress.CompressGzip;
import ch.erebetez.decode.utils.FileWriters;

public class CompressTest {

		@Test
		public void uncompressSmallGZip() throws IOException {
			
			String compressedData = FileWriters.readFile(getClass().getClassLoader().getResource("myBaseZip.txt"));
			
			String valueString = "Hello";
			
			
			Compress comp = new CompressGzip(valueString);
			
			System.out.println(compressedData);
			System.out.println(comp.compress());
			Assert.assertEquals(compressedData, comp.compress());

		}

		@Test
		public void comprssEatXmlHalloSmall() throws IOException {
			
			Compress comp = new CompressEat("Hallo");
			
			String compressedReference = FileWriters.readFile(getClass().getClassLoader().getResource("HalloString.txt"));
			
			System.out.println(compressedReference);
			
			String compressed = comp.compress();
			
			System.out.println(compressed);
			
			Assert.assertEquals(compressedReference, compressed);

		}
		
		@Test
		public void comprssEatBiggerXml() throws IOException {
			
			Compress comp = new CompressEat(FileWriters.readFile(getClass().getClassLoader().getResource("biggerXml.xml"), "windows-1252"));
			
			String compressedReference = FileWriters.readFile(getClass().getClassLoader().getResource("biggerXml.txt"));
			
			System.out.println(compressedReference);
			
			String compressed = comp.compress();
			
			System.out.println(compressed);
			
			Assert.assertEquals(compressedReference, compressed);

		}
		
		@Test
		public void comprssEatMarshallXml() throws IOException {
			
			Compress comp = new CompressEat(FileWriters.readFile(getClass().getClassLoader().getResource("marshallXmlSmall.xml"), "windows-1252"));
			
//			String compressedReference = FileWriters.readFile("resources/marshallXmlSmall.txt");
			
//			System.out.println(compressedReference);
			
			String compressed = comp.compress();
			
			System.out.println(compressed);
			
//			Assert.assertEquals(compressedReference, compressed);

		}		
		
}
