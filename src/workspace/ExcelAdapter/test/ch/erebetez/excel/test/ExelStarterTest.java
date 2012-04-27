package ch.erebetez.excel.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


import junit.framework.Assert;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.erebetez.excel.*;

public class ExelStarterTest {
	static EatStarter excel = null;
	
	@BeforeClass
	public static void init(){
		if (excel == null){
		    excel = new EatStarter();
		}
	}
	
	@AfterClass
	public static void close(){
		
		System.out.println(excel.getStartedExcelList());
	}
	
	@Test
	public void copyAFile() {
		
		List<String> parameterList = Arrays.asList("test", "1235"); 
		
    	Assert.assertEquals("dummy copy", "open;test;1235.xlsm", excel.parameterToFileName(parameterList));

	}	

	@Test
	public void startEatExel() {
		excel.startEat("A126");
	}

	@Test
	public void startEatExelWithParam() {
		excel.startEat(Arrays.asList("A127.8", "WL2012003366"));
	}
	
}
