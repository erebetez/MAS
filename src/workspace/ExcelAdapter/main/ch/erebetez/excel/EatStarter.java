/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
    Starts a specific type of excel.
    Copyright (C) 2012  Etienne Rebetez

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */

package ch.erebetez.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import ch.erebetez.util.ProcessExitDetector;
import ch.erebetez.util.ProcessListener;

public class EatStarter {
	private ProcessExitDetector processExitDetector;
	
	private List<String> startedExcelList = null;

	public List<String> getStartedExcelList() {
		return startedExcelList;
	}

	private Path excelPath = FileSystems.getDefault().getPath("C:",
			"Program Files", "Microsoft Office", "Office12", "EXCEL.EXE");

	private Path templatePath = FileSystems.getDefault().getPath(
			"\\\\euchbrnfil01", "data", "Bioplasma", "Quality Assurance", "QC",
			"QC_Vorlagen", "ExcelAppl", "VISION", "NotOpenTemplate.xlsm");

	private Path localPath = FileSystems.getDefault().getPath("C:", "qc");

	public EatStarter(){
		startedExcelList = new Vector<String>();
	}

	public void startEat(String appName){
		if (appName == "" || appName == null){
			return;
		}
		startEat( Arrays.asList(appName) );
	}	
	
	public void startEat(List<String> parameterList){
		if(parameterList == null){
			throw new IllegalArgumentException(
					"parameterList can't be empty");
		}		

		try {
			String tempExcelFile = copyExcelTemplate(parameterToFileName( parameterList ));
			startedExcelList.add(tempExcelFile);
			
			runExcel(tempExcelFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public void runExcel(final String tempExcelFile) {
		
		try {

			ProcessBuilder builder = new ProcessBuilder(excelPath.toString(),
					tempExcelFile);

			System.out.println("excel start");
					
		    processExitDetector = new ProcessExitDetector(builder.start());
		    processExitDetector.addProcessListener(new ProcessListener() {
		        public void processFinished(Process process) {
		            System.out.println("The subprocess has finished.");
		            cleanUp(tempExcelFile);
		        }
		    });
		    processExitDetector.start();

		} catch (IOException e1) {
			System.out.println("IOException");
			cleanUp(tempExcelFile);
			e1.printStackTrace();
		}
		
		System.out.println("The end");
	}
	
	public String copyExcelTemplate(String newFileName ) throws IOException {
		Path newExcelPath = FileSystems.getDefault().getPath(
				localPath.toString(), newFileName);

		return Files.copy(templatePath, newExcelPath).toString();
	}

	public String parameterToFileName(List<String> parameterList) {
		StringBuffer fileName = new StringBuffer();

		fileName.append("open");

		for (String element : parameterList) {
			fileName.append(";" + element);
		}

		fileName.append(".xlsm");

		return fileName.toString();
	}

	public void cleanUp(String tempExcelFile) {

		File f = new File(tempExcelFile);
		f.delete();

	}
}
