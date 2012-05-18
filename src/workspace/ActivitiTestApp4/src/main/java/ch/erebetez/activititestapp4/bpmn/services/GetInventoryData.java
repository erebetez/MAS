package ch.erebetez.activititestapp4.bpmn.services;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class GetInventoryData implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		
		
		int inventoryCount = 3;
		
		// TODO HashMap...
		
		execution.setVariable("InventoryCount", inventoryCount);

	}
}
