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
