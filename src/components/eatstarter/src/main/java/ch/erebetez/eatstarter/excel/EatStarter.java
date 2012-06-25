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

package ch.erebetez.eatstarter.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import ch.erebetez.eatstarter.util.*;

/**
 * Class to start eat applications chains.
 * 
 * To start the eat chain a Excel File is used as a template. This 
 * Template is renamed into something like:
 * open;A100;param1.xlsm
 * 
 * If this renamed excel template is open, the excel subroutine onOpen is
 * run. The Name is then parsed (separator ;) into the parameters.
 * The subroutines only continues if the first parameter is open.
 * The second parameter is the eat application to start.
 * The remaining parameters are then given to the started application.
 * 
 * @author      Etienne Rebetez
 * @version     %I%, %G%
 * @since       1.0
 */
public class EatStarter {
	private ProcessExitDetector processExitDetector;
	
	private List<String> startedExcelList = null;

	public List<String> getStartedExcelList() {
		return startedExcelList;
	}

	private final Path excelPath = FileSystems.getDefault().getPath("C:",
			"Program Files", "Microsoft Office", "Office12", "EXCEL.EXE");

	private final Path templatePath = FileSystems.getDefault().getPath(
			"\\\\euchbrnfil01", "data", "Bioplasma", "Quality Assurance", "QC",
			"QC_Vorlagen", "ExcelAppl", "VISION", "NotOpenTemplate.xlsm");

	private final Path localPath = FileSystems.getDefault().getPath("C:", "qc");
	
	private final String parameterSeparator = ";";

	private List<FinishedListener> listeners = new Vector<FinishedListener>();
	
	public EatStarter(){
		startedExcelList = new Vector<String>();
	}
	
    /**
     * When the eat application process has finished the FinishedListener
     * signal is fired. 
 	 * @param finishedListener the listener
	 */
	public void addListener(FinishedListener finishedListener){
		this.listeners.add(finishedListener);
	}

    /**
     * Starts the eat application by the application name without parameters.
 	 * @param appName Starts by application name
	 */	
	public void startEat(String appName){
		if (appName == "" || appName == null){
			return;
		}
		startEat( Arrays.asList(appName) );
	}	
	
    /**
     * Starts the eat application with the given parameter.
     * The first parameter has to be the application name.
     * 
 	 * @param parameterList parameter list
	 */	
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
	
	
	private void runExcel(final String tempExcelFile) {
		
		try {

			ProcessBuilder builder = new ProcessBuilder(excelPath.toString(),
					tempExcelFile);
					
		    processExitDetector = new ProcessExitDetector(builder.start());
		    processExitDetector.addProcessListener(new ProcessListener() {
		        public void processFinished(Process process) {

		            cleanUp(tempExcelFile);

		            for(FinishedListener listener: listeners){
		            	listener.finished();
		            }
		        }
		    });
		    processExitDetector.start();

		} catch (IOException e1) {
			cleanUp(tempExcelFile);
			e1.printStackTrace();
		}
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
			fileName.append(parameterSeparator + element);
		}

		fileName.append(".xlsm");

		return fileName.toString();
	}

	public void cleanUp(String tempExcelFile) {

		File f = new File(tempExcelFile);
		f.delete();

	}
	
}
