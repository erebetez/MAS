package ch.erebetez.activititestapp4.bpmn.services;

import java.util.Arrays;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import ch.erebetez.activititestapp4.dataadapter.*;

public class GetInventoryData implements JavaDelegate {

	private final List<String> lotIDs = Arrays.asList( "123456", "123455", "125476", "122234", "123457" );
	
	public void execute(DelegateExecution execution) {		
		
		DataAdapter dataList = new DummyDataAdapter();
	
//		execution.setVariable("InventorySize", dataList.size());
		execution.setVariable("InventoryData", dataList.getData());
        
	}

}
