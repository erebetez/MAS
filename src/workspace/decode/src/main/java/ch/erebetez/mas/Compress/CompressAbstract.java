package ch.erebetez.mas.Compress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

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

	
}
