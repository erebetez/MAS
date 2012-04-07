package ch.erebetez.mas;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SdmsAdapter implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		String name = (String) execution.getVariable("worklist");
		
		System.out.println("Getting Data for : " + name);
		
		int varB = 9;
		
		execution.setVariable("varB", varB);

	}
}
