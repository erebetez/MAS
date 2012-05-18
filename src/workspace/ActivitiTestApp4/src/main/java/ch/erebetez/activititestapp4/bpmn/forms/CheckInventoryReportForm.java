package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.Map;

import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;
import com.vaadin.ui.TextField;

public class CheckInventoryReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355795992791143870L;

	public static final String FORM_KEY = "checkInventoryResultsForm";

	private TextField comments;

	@Override
	public String getDisplayName() {
		return "Is the inventory Ok";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	protected void populateFormInit(String taskId, String executionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("checkComment")) {
//			comments.setValue(propertyValue);
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
