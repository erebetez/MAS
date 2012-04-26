package ch.erebetez.mas.Compress;

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

	CompressAbstract(String payload) {		
		this(payload, "UTF-8");
	}

	CompressAbstract(String payload, String charset) {
		super();
		this.charset = charset;
		this.payload = payload;
	}
	
	public String encodeDataToBase64(){
		return Base64.encodeBase64URLSafeString( this.compressedData );
	}
	
	public void saveOutputStreamAsByteArray(OutputStream outputStream){
		this.compressedData = ((ByteArrayOutputStream) outputStream).toByteArray();
	}
	
}
