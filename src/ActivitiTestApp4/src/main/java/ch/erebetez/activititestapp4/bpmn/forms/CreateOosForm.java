package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.Map;

import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;
import com.vaadin.ui.TextField;

public class CreateOosForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355795992791143870L;

	public static final String FORM_KEY = "startOOS";

	private TextField oosNumber;

	@Override
	public String getDisplayName() {
		return "Create an OOS and add Number here.";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	protected void populateFormInit(String taskId, String executionId) {
		// Not needed
	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("oosNumber")) {
//			comments.setValue(propertyValue);
		}
	}
	
	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("oosNumber", (String) oosNumber.getValue());
	}
	
	@Override
	protected void init() {
		oosNumber = new TextField("OOS Number");
		addComponent(oosNumber);
	}

}
