package ch.erebetez.mas;

import junit.framework.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;

public class UnZiptest extends TestCase {

	public void test() throws IOException {

		StringBuilder text = new StringBuilder();

		Scanner scanner = new Scanner(
				new FileInputStream(
						"resources/myhy.txt"));


		try {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}

		System.out.println("Text read in: " + text);

		String base = text.toString();

		byte[] send = base.getBytes();
		//
		// System.out.println(send);
		//
		// byte[] base = Base64.encodeBase64(send);
		//
		//
		// Assert.assertTrue(Base64.isBase64(base));

		System.out.println(Charset.defaultCharset());
		System.out.println(Charset.availableCharsets());

		// windows-1253

		// String bytes2 = new String(Base64.decodeBase64(send),
		// Charset.forName("ISO-8859-1"));
		//
		// System.out.println(bytes2);

		ByteArrayInputStream bais = new ByteArrayInputStream(
				Base64.decodeBase64(send));
		GZIPInputStream gzis = new GZIPInputStream(bais);
		InputStreamReader reader = new InputStreamReader(gzis);
		BufferedReader in = new BufferedReader(reader);

		String readed;
		while ((readed = in.readLine()) != null) {
			System.out.println(readed);
		}

		// String decode = new String(bytes2);

		// Assert.assertEquals(send, bytes2);

	}

}
