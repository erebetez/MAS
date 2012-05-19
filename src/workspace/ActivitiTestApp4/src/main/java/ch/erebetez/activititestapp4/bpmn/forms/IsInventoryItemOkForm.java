package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.Map;


import ch.erebetez.activititestapp4.dataobjects.InventoryItem;
import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;


public class IsInventoryItemOkForm extends AbstractUserTaskForm {
	private static final long serialVersionUID = -2630573261987924112L;

	
	public static final String FORM_KEY = "isItemOkForm";
	
	private static final ThemeResource okIcon = new ThemeResource(
			"../runo/icons/32/ok.png");
	private static final ThemeResource cancelIcon = new ThemeResource(			
			"../runo/icons/32/cancel.png");
	
	private boolean isOK = false;

	private Button okButton = null;
	private Button nokButton = null;
	
	private Label verboseVerdict = null;

	private Button getOkButton() {
		if (okButton == null) {
			okButton = new Button("Item Ok");
			okButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 6618266666293209903L;

				@Override
				public void buttonClick(ClickEvent event) {					
					setVerdictOk();
					showSelection();
				}
			});
		}
		return okButton;
	}	

	private Button getNokButton() {
		if (nokButton == null) {
			nokButton = new Button("Item NOT Ok");
			nokButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 6618266666293209903L;

				@Override
				public void buttonClick(ClickEvent event) {
					setVerdictNok();
					showSelection();
				}
			});

		}
		return nokButton;
	}

	
	private Label getVerboseVerdict(){
		if( verboseVerdict == null){
			verboseVerdict = new Label();
			
		}		
		return verboseVerdict;
	}
	
	private void setVerdictNok(){
		isOK = false;
		getVerboseVerdict().setCaption("Not OK. OOS shal be created!");
		getVerboseVerdict().setIcon(cancelIcon);
	}
	
	private void setVerdictOk(){
		isOK = true;
		getVerboseVerdict().setCaption("OK");
		getVerboseVerdict().setIcon(okIcon);		
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
	protected void populateFormInit(String taskId, String executionId) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> itemData = (Map<String, String>) getVariable("itemData");
		
		addComponent(new Label("Is Sample ok?"));

		if( itemData == null ){
			addComponent(new Label("No Data available."));			
		} else {
			addComponent(new Label("Lot: " + itemData.get(InventoryItem.LOT)));
			addComponent(new Label("Room: " + itemData.get(InventoryItem.LOCATION)));			
		}		
				
		HorizontalLayout layout = new HorizontalLayout();
		addComponent(layout);
		
		layout.addComponent(getOkButton());
		layout.addComponent(getNokButton());
		
		addComponent(getVerboseVerdict());
		
		// Initaly set Not OK.
		setVerdictNok();
		
	}
	
	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("isItemOk")) {
			// Not Used
		}
	}
	
	@Override
	public void copyFormProperties(Map<String, String> destination) {

		destination.put("isItemOk", new Boolean(isOK).toString());

	}
	
	@Override
	protected void init() {


	}

	public void showSelection() {
		getWindow().showNotification(
				String.format("The Item is %s", isOK ? "Ok" : "Not Ok"),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}



}
