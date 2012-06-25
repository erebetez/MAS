package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.Map;

import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class InputCalculationValues extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355795992791143870L;

	public static final String FORM_KEY = "inputCalculationValues";

	private TextField value01;
	private TextField value02;
	private TextField value03;

	@Override
	public String getDisplayName() {
		return "Provide som data.";
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
		if (propertyId.equals("value01")) {
			value01.setValue(propertyValue);
		}
		if (propertyId.equals("value02")) {
			value02.setValue(propertyValue);
		}
		if (propertyId.equals("value03")) {
			value03.setValue(propertyValue);
		}		
	}
	
	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("value01", (String) value01.getValue());
		destination.put("value02", (String) value02.getValue());
		destination.put("value03", (String) value03.getValue());
	}
	
	@Override
	protected void init() {
		Label info = new Label("Values to summ up");
		value01 = new TextField("Value 01");
		value02 = new TextField("Value 02");
		value03 = new TextField("Value 03");

		addComponent(info);
		addComponent(value01);
		addComponent(value02);
		addComponent(value03);
	}

}
