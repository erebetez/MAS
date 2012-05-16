package ch.erebetez.activititestapp4.ui.widgets;

import java.util.logging.Logger;

import org.activiti.engine.FormService;
import org.activiti.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)
public class FormViewer  extends CustomComponent{
	private static final long serialVersionUID = -6103590408722895618L;

	private static Logger log = Logger.getLogger(AbstractUserTaskForm.class
			.getName());	
	
	@Autowired
	protected FormService formService;			


	private TextField testTestField = null;

	private Button submitButton = null;
	
	private UserTaskFormContainer userTaskFormContainer;

	private UserTaskForm currentForm;
	
	private VerticalLayout formContainerLayout;
	
	private UserTaskForm form;
	
	public FormViewer(UserTaskFormContainer userTaskFormContainer){
		this.userTaskFormContainer = userTaskFormContainer;
		
		Panel panel = new Panel("Form");
		panel.setContent(new VerticalLayout());

		formContainerLayout = new VerticalLayout();
		
//		panel.addComponent(getTestTestField());
		panel.addComponent(formContainerLayout);
		panel.addComponent(getSubmitButton());

        setCompositionRoot(panel);

	}
	
	public TextField getTestTestField() {
		if (testTestField == null){
			testTestField = new TextField("The Textfield");
			testTestField.setEnabled(false);
		}
		return testTestField;
	}
	
	public Button getSubmitButton() {
		if(submitButton == null){
			submitButton = new Button("Submit");
			
			submitButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -6441664988506039946L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					submitForm(form);
				}
			});
		}
		return submitButton;
	}
	
	public void setTask(){
		
	}
	
	public void showTaskForm(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);		
		UserTaskForm form = userTaskFormContainer.getForm(formData.getFormKey());
		this.form = form;
		form.populateForm(formData, taskId);
		setForm(form);
	}

	public void setForm(UserTaskForm form) {
		currentForm = form;
		updateControls();
	}


	public void hideForm() {
		currentForm = null;
		updateControls();
	}

	private void updateControls() {
//		updateHeaderLabel();
//		submitButton.setVisible(currentForm != null);
		formContainerLayout.removeAllComponents();
		if (currentForm != null) {
			formContainerLayout.addComponent(currentForm.getFormComponent());
		}
	}
	
	
	public void submitForm(UserTaskForm form) {
		if (form.getFormType().equals(UserTaskForm.Type.START_FORM)) {
			formService.submitStartFormData(form.getProcessDefinitionId(),
					form.getFormProperties());
			
		} else if (form.getFormType().equals(UserTaskForm.Type.TASK_FORM)) {
			formService.submitTaskFormData(form.getTaskId(),
					form.getFormProperties());
		}
	}


//	private void populateForm(TaskFormData formData, String taskId) {
//
//		populateFormFields(formData);
//	}
//
//	protected void populateFormFields(FormData formData) {
//		log.info("Populating form fields");
//		for (FormProperty property : formData.getFormProperties()) {
//			String propertyId = property.getId();
//			String propertyValue = property.getValue();
//			log.log(Level.INFO, "Populating form field {1} with value {2}",
//					new Object[] { propertyId, propertyValue });
//			populateFormField(propertyId, propertyValue);
//		}
//	}
	

	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("dilution")) {
			testTestField.setValue(propertyValue);
		}
	}


}
