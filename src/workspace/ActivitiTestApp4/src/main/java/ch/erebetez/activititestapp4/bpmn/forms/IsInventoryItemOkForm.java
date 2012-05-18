package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.Map;

import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

public class IsInventoryItemOkForm extends AbstractUserTaskForm {
	private static final long serialVersionUID = -2630573261987924112L;

	public static final String FORM_KEY = "isItemOkForm";

    private boolean isOK = false;

	private Button okButton = null;
	private Button nokButton = null;

    public Button getOkButton() {
    	if (okButton == null){
    		okButton = new Button("Item Ok");
    		okButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 6618266666293209903L;

					@Override
    				public void buttonClick(ClickEvent event) {
    					isOK = true;
    					showSelection();
    				}
    			});   		
    	}
		return okButton;
	}
	

    public Button getNokButton() {
    	if (nokButton == null){
    		nokButton = new Button("Item NOT Ok");
    		nokButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 6618266666293209903L;

				@Override
				public void buttonClick(ClickEvent event) {
					isOK = false;
					showSelection();
				}
			});
    		
    	}
		return nokButton;
	}
	
	@Override
	public String getDisplayName() {
		return "Is the Item Ok?";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		
		destination.put("isItemOk", new Boolean(isOK).toString());
		
	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("isItemOk")) {
           // Not Used
			
		}
	}

	@Override
	protected void init() {
		HorizontalLayout layout = new HorizontalLayout();			
		addComponent(layout);
		
		layout.addComponent(getOkButton());
		layout.addComponent(getNokButton());
	}
	
	public void showSelection() {
		getWindow().showNotification(
				String.format("The Item is %s", isOK?"Ok":"Not Ok"),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	

}
