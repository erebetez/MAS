package ch.erebetez.mas;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;


import org.apache.commons.codec.binary.Base64;

import com.jcraft.jzlib.*;

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

//		try {
//			inputStream = new GZIPInputStream(base64Decode());
//		} catch (IOException e) {
//			
		
	    	try {
	    		
	    		byte[] source = Base64.decodeBase64(base64);
	    		
	    		writeByteToFile(source);

	    		String zipValue = new String(source);
	    		
	    		
	    		
	    		int delimiterPos = zipValue.indexOf(":");
	    		System.out.println("delpos: " + delimiterPos);
	    		
	    		
	    		String theHexLength = zipValue.substring(0, delimiterPos);
	    		
	    		System.out.println(theHexLength);	    
	    		
	    		int theLength = Integer.valueOf(theHexLength, 16).intValue();
	    		
	    		System.out.println(theLength);	
	    		
	    		// No idea why the position has to be shifted 3 bytes.
	    		source = Arrays.copyOfRange(source, delimiterPos + 3, source.length);
	    		
	    		
				inputStream = new InflaterInputStream( new ByteArrayInputStream(source), new Inflater(JZlib.DEF_WBITS, true));
				

			} catch (IOException e1) {

				e1.printStackTrace();
			}
		
//		}
		



		try {
			this.value = readInputStream(inputStream);
		} catch (IOException e) {
			this.value = "";
			e.printStackTrace();
		}
	}

	private InputStream base64Decode(){

		// does not seam to be necessary. automatic url safe dedetciont or so...
	    base64 = base64.replace('_', '/');
	    base64 = base64.replace('-', '+');
	    		
		byte[] source = Base64.decodeBase64(base64);

		byte[] bytLength = Arrays.copyOfRange(source, 0, 2);
		

		System.out.println("the Stirng");
		System.out.println(Arrays.toString(source));
		
		String length = Arrays.toString(bytLength);
		System.out.println(length);
		
		System.out.println("bytelengt: " + source.length);
		
		
		source = Arrays.copyOfRange(source, 0, source.length);
		
		System.out.println("bytelengt: " + source.length);

		return new ByteArrayInputStream(source);
	}
	
	
	private void writeByteToFile(byte[] source){
		BufferedOutputStream bos = null;		
		try {
			
			bos = new BufferedOutputStream(new FileOutputStream(new File("outfilename.txt")));
			
			bos.write(source);
			bos.close();
        }
        catch(FileNotFoundException fnfe)
        {
                System.out.println("Specified file not found" + fnfe);
        }
        catch(IOException ioe)
        {
                System.out.println("Error while writing file" + ioe);
        }
        finally
        {
                if(bos != null)
                {
                        try
                        {
                       
                                //flush the BufferedOutputStream
                        	bos.flush();
                               
                                //close the BufferedOutputStream
                        	bos.close();
                       
                        }
                        catch(Exception e){}
                }
        }
		
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

		System.out.println(readed.length());
		
		return readed.toString();
	}
	
}
