package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.List;
import java.util.Map;

import ch.erebetez.activititestapp4.dataobjects.InventoryItem;
import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;

import com.vaadin.ui.Label;
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
		// TODO show some actuall data from the subprocesses.
		
		@SuppressWarnings("unchecked")
		List<Map<String, String>> var = (List<Map<String, String>>) getVariable("itemDataReturn");

		if (var == null) {
			addComponent(new Label("No data from sub."));
		} else {
			for (int i = 0; i < var.size(); ++i) {
				String loc = var.get(0).get(InventoryItem.LOCATION);
				addComponent(new Label("local : " + loc));
			}
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
