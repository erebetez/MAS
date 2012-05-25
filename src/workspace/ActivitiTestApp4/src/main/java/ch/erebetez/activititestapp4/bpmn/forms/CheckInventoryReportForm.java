package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;

import ch.erebetez.activititestapp4.dataobjects.InventoryItem;
import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class CheckInventoryReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355795992791143870L;

	public static final String FORM_KEY = "checkInventoryResultsForm";

	private TextField comments;
	
	private class invetoryTaskDao {
		
		
	}
	

	@Override
	public String getDisplayName() {
		return "Is the inventory review Ok";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	protected void populateFormInit(String taskId, String executionId) {
		// TODO show some actuall data from the subprocesses.

		List<HistoricProcessInstance> subHist = getHistoryService().createHistoricProcessInstanceQuery().superProcessInstanceId(getTask().getProcessInstanceId()).list();
		
		for(HistoricProcessInstance subInst: subHist){
			List<HistoricDetail> detailHist = getHistoryService().createHistoricDetailQuery().processInstanceId(subInst.getId()).list();
			
		}

	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("checkComment")) {
			// comments.setValue(propertyValue);
		}
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("checkComment", (String) comments.getValue());
	}

	@Override
	protected void init() {
		comments = new TextField("Comment");
		addComponent(comments);
	}

}
