package ch.erebetez.mas;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;

public class UnCompress {
	String base64 = null;
	String charset = null;
	String value = null;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getBase64() {
		return base64;
	}

	public String getValue() {
		uncompress();
		return value;
	}

	UnCompress(String base64) {
		this.base64 = base64;
		this.charset = Charset.defaultCharset().toString();
	}

	UnCompress(String base64, String charset) {
		this.base64 = base64;
		this.charset = charset;
		
	}

	private void uncompress() {

		InputStream inputStream = null;

		try {
			inputStream = new GZIPInputStream(base64Decode());
		} catch (IOException e) {
			inputStream = base64Decode();
		}

		try {
			this.value = readInputStream(inputStream);
		} catch (IOException e) {
			this.value = "";
			e.printStackTrace();
		}
	}

	private InputStream base64Decode(){
		return new ByteArrayInputStream(
				Base64.decodeBase64(base64.getBytes()));
	}
	
	private String readInputStream(InputStream inputStream) throws IOException {

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
