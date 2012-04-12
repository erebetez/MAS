package ch.cslbehring.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ExelStarterTest {

	@Test
	public void test() {
		EatStarter excel = new EatStarter();
		
		Assert.assertEquals("dummy", excel.name(), "Starts Excel" );
	}
	
	@Test
	public void copyAFile() {
		EatStarter excel = new EatStarter();
		
		List<String> parameterList = Arrays.asList("test");
		
		excel.setParameterList(parameterList);
		
		try {
			Assert.assertNotNull("dummy copy", excel.copyExcelTemplate());
		} catch (IOException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Path newFile = FileSystems.getDefault().getPath("C:", "qc", "open;test.xlsm");
		Assert.assertNotNull(newFile);
		
		File f = new File(newFile.toString());
		f.delete();
	}	

	@Test
	public void startEatExel() {
		EatStarter excel = new EatStarter();
		
		try {
			Assert.assertEquals("Starts Limit checker", excel.start("A126"), true );
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void startEatExelWithParam() {
		EatStarter excel = new EatStarter();
		
		excel.setParameterList(Arrays.asList("A127.8", "WL2012003366"));
		
		try {
			Assert.assertEquals("Start Sequencebuilder", excel.start(), true );
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
}
