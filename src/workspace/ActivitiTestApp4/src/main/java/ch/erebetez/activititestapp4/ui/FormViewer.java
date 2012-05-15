package ch.erebetez.activititestapp4.ui;

import org.activiti.engine.FormService;
import org.activiti.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.CustomComponent;

@Configurable(preConstruction = true)
public class FormViewer  extends CustomComponent{
	private static final long serialVersionUID = -6103590408722895618L;
	
	@Autowired
	protected FormService formService;			
	
	
	public FormViewer(){
		
		
		
	}
	
	
	public void populateForm(){
//		UserTaskForm form = userTaskFormContainer.getForm(formKey);
//		TaskFormData formData = getFormService().getTaskFormData(taskId);
//		form.populateForm(formData, taskId);
//		getView().setForm(form);
	}
	
	public void setTask(){
		
	}
	
}
