package ch.erebetez.mas.Compress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;

public class CompressGzip extends CompressAbstract {

	CompressGzip(String payload) {
		super(payload);
	}

	@Override
	public String compress() {
		String returnValue = null;
		
		OutputStream outputStream = new ByteArrayOutputStream ();
		GZIPOutputStream outGzip = null;
//		Base64OutputStream outBase64 = null;
		
		try {
			outGzip = new GZIPOutputStream( outputStream );
//			outBase64 = new Base64OutputStream( outGzip );
			
			outGzip.write(super.payload.getBytes(super.charset));
			outGzip.close();

			
			returnValue = Base64.encodeBase64URLSafeString( ((ByteArrayOutputStream) outputStream).toByteArray() );
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return returnValue;

	}
	
	

}
