package ch.erebetez.mas.UnCompress;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class UnCompressGzip extends UnCompressAbstract {

	UnCompressGzip(String payload) {
		super(payload);

	}

	@Override
	public String uncompress() {
		String returnData = null;

		InputStream inputStream = null;
		ByteArrayInputStream bufferStream = new ByteArrayInputStream(
				super.compressedData);

		try {
			inputStream = new GZIPInputStream(bufferStream);

			returnData = super.readInputStream(inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnData;
	}

}
