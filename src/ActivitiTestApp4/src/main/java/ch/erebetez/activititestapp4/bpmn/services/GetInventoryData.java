package ch.erebetez.activititestapp4.bpmn.services;

import java.util.Arrays;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import ch.erebetez.activititestapp4.dataadapter.*;

public class GetInventoryData implements JavaDelegate {
	
	public void execute(DelegateExecution execution) {
		
		DataAdapter dataList = new DummyDataAdapter();

		execution.setVariable("InventoryData", dataList.getData());
        
	}

}
