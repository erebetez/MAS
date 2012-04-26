package ch.erebetez.mas.Compress;


import java.io.IOException;


import junit.framework.Assert;

import org.junit.Test;

import ch.erebetez.utils.FileWriters;

public class CompressTest {

		@Test
		public void uncompressGZip() throws IOException {

			String compressedData = FileWriters.readFile("resources/myBaseZip.txt");
			
			String valueString = "Hello";
			
			
			Compress comp = new CompressGzip(valueString);
			
			System.out.println(compressedData);
			System.out.println(comp.compress());
			Assert.assertEquals(compressedData, comp.compress());
		

		}

}
