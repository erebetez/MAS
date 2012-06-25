package ch.erebetez.activititestapp4.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.FormService;

import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.*;
import ch.erebetez.activititestapp4.ui.RefreshListener;
import ch.erebetez.activititestapp4.ui.util.UserTaskForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)
public class FormViewer  extends CustomComponent implements ShowFormListener{
	private static final long serialVersionUID = -6103590408722895618L;

	@Autowired
	protected FormService formService;

	@Autowired
	private I18nManager i18n;

	private Button submitButton = null;
	
	private UserTaskFormContainer userTaskFormContainer;

	private UserTaskForm currentForm;
	
	private VerticalLayout formContainerLayout;
	
	private UserTaskForm form;
	
	private List<RefreshListener> refreshListeners = new ArrayList<RefreshListener>();
	
	public FormViewer(UserTaskFormContainer userTaskFormContainer){
		this.userTaskFormContainer = userTaskFormContainer;
		
		Panel panel = new Panel(i18n.get(Messages.ACTIVITI_FORM));
		panel.setContent(new VerticalLayout());
		panel.setSizeFull();

		formContainerLayout = new VerticalLayout();
		
		panel.addComponent(formContainerLayout);
		panel.addComponent(getSubmitButton());

        setCompositionRoot(panel);

	}
	
	
	public Button getSubmitButton() {
		if(submitButton == null){
			submitButton = new Button(i18n.get(Messages.BUTTON_SUBMIT));
			
			submitButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -6441664988506039946L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					submitForm(form);
					hideForm();
				}
			});
		}
		return submitButton;
	}
	
	public void showTaskForm(Task task) {
		TaskFormData formData = formService.getTaskFormData(task.getId());
		UserTaskForm form = userTaskFormContainer.getForm(formData.getFormKey());
		this.form = form;
		form.populateForm(formData, task);
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

		formContainerLayout.removeAllComponents();

		if (currentForm != null) {
			formContainerLayout.addComponent(currentForm.getFormComponent());
			getSubmitButton().setEnabled(true);
		} else {
			getSubmitButton().setEnabled(false);
		}
		
        for (RefreshListener listener : refreshListeners) {
            listener.refresh();
        }
	}
	
	
	public void submitForm(UserTaskForm form) {
		if (form.getFormType().equals(UserTaskForm.Type.START_FORM)) {
//			formService.submitStartFormData(form.getProcessDefinitionId(),
//					form.getFormProperties());

		} else if (form.getFormType().equals(UserTaskForm.Type.TASK_FORM)) {
			formService.submitTaskFormData(form.getTask().getId(),
					form.getFormProperties());
		}
	}

	public void addListener(RefreshListener listener){
		refreshListeners.add(listener);
	}	

	@Override
	public void showFormForTask(Task task) {
		showTaskForm(task);		
	}


}
