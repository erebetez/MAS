package ch.erebetez.mas.UnCompress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public abstract class UnCompressAbstract implements UnCompressInterface {
	byte[] compressedData = null;
	String charset = null;

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
