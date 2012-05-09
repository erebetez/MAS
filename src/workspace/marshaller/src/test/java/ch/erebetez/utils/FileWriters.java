package ch.erebetez.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWriters {

	public static void writeByteToFile(byte[] source){
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

	public static void wirteStringToFile(String source){
		BufferedWriter  bos = null;		
		try {
			
			bos = new BufferedWriter ((new FileWriter("outfilename.txt")));
			
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
	

	public static String readFile(String filename) {
		return readFile(filename, "UTF-8");
	}
	
	public static String readFile(String filename, String charset) {
		StringBuilder text = new StringBuilder();

		Scanner scanner;
		try {
			scanner = new Scanner(
					new FileInputStream(filename), charset);
			try {
				while (scanner.hasNextLine()) {
					text.append(scanner.nextLine());
				}
			} finally {
				scanner.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		System.out.println("Text read in: " + text);

		return text.toString();
	}
	
}
