package ch.cslbehring.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class EatStarter {
	private String tempExcelFile = null;
	
	private List<String> parameterList = null;

	public List<String> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<String> parameterList) {
		this.parameterList = parameterList;
	}

	private Path excelPath = FileSystems.getDefault().getPath("C:",
			"Program Files", "Microsoft Office", "Office12", "EXCEL.EXE");

	private Path templatePath = FileSystems.getDefault().getPath(
			"\\\\euchbrnfil01", "data", "Bioplasma", "Quality Assurance", "QC",
			"QC_Vorlagen", "ExcelAppl", "VISION", "NotOpenTemplate.xlsm");

	private Path localPath = FileSystems.getDefault().getPath("C:", "qc");

	public String name() {
		return "Starts Excel";
	}

	public boolean start() throws IOException {

		if (this.parameterList == null) {
			return false;
		}

		this.tempExcelFile = copyExcelTemplate().toString();
		
		ProcessBuilder builder = new ProcessBuilder(excelPath.toString(),
				this.tempExcelFile);

		Process p = builder.start();
		
		try {
			// Blocks the thread
			p.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cleanUp();

		return true;
	}

	public boolean start(List<String> parameterList) throws IOException {
		this.parameterList = parameterList;
		return start();
	}

	public boolean start(String appName) throws IOException {
		this.parameterList = Arrays.asList(appName);
		return start();
	}

	public Path copyExcelTemplate() throws IOException {
		Path newExcelPath = FileSystems.getDefault().getPath(
				localPath.toString(), parameterToFileName());

		return Files.copy(templatePath, newExcelPath);
	}

	public String parameterToFileName() {
		StringBuffer fileName = new StringBuffer();

		fileName.append("open");

		for (String element : this.parameterList) {
			fileName.append(";" + element);
		}

		fileName.append(".xlsm");

		return fileName.toString();
	}

	public void cleanUp() {
		
		File f = new File(this.tempExcelFile);
		f.delete();
       
	}
}
