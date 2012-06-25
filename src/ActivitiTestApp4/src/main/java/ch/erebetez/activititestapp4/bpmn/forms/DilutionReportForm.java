package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.Map;

import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;
import com.vaadin.ui.TextField;

public class DilutionReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355795992791143870L;

	public static final String FORM_KEY = "dilutionReportForm";

	private TextField dilution;

	@Override
	public String getDisplayName() {
		return "Input the used dilution";
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
		if (propertyId.equals("dilution")) {
			dilution.setValue(propertyValue);
		}
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("dilution", (String) dilution.getValue());
	}
	
	@Override
	protected void init() {
		dilution = new TextField("Dilution");
		dilution.setEnabled(true);
		dilution.setRequired(true);
		addComponent(dilution);
	}



}
