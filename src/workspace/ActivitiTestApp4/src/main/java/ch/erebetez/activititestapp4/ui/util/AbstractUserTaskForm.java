package ch.erebetez.activititestapp4.ui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@Configurable(preConstruction = true)
public abstract class AbstractUserTaskForm extends VerticalLayout implements
		UserTaskForm {
	private static final long serialVersionUID = -1578037829262126003L;
	
	@Autowired
	private RuntimeService runtimeservice;
	
	@Autowired
	private HistoryService historyService;	
	
	private static Logger log = Logger.getLogger(AbstractUserTaskForm.class
			.getName());

	// TODO support processDefinitions (Start forms)
//	private String processDefinitionId;

	private Task task;

	public AbstractUserTaskForm() {
		init();
	}

	protected abstract void init();

	@Override
	public Type getFormType() {
		if (task != null) {
			return Type.TASK_FORM;
//		} else if (processDefinitionId != null) {
//			return Type.START_FORM;
		} else {
			throw new IllegalStateException(
					"No taskId nor processDefinitionId has been specified");
		}
	}

//	@Override
//	public String getProcessDefinitionId() {
//		return processDefinitionId;
//	}

	@Override
	public Task getTask() {
		return this.task;
	}

//	@Override
//	public void populateForm(StartFormData formData, String processDefinitionId) {
//		this.processDefinitionId = processDefinitionId;
//		populateFormFields(formData);
//	}

	@Override
	public void populateForm(TaskFormData formData, Task task) {
		this.task = task;
		populateFormInit( task.getId(), task.getExecutionId() );
		populateFormFields(formData);
	}

	protected void populateFormFields(FormData formData) {
		log.info("Populating form fields");
		for (FormProperty property : formData.getFormProperties()) {
			String propertyId = property.getId();
			String propertyValue = property.getValue();
			log.log(Level.INFO, "Populating form field {1} with value {2}",
					new Object[] { propertyId, propertyValue });
			populateFormField(propertyId, propertyValue);
		}
	}

	@Override
	public Map<String, String> getFormProperties() {
		log.log(Level.INFO, "Constructing form property map for {1}",
				getFormKey());
		HashMap<String, String> map = new HashMap<String, String>();
		copyFormProperties(map);
		return map;
	}

	
	public Object getVariable(String key){
		return runtimeservice.getVariable(task.getExecutionId(), key); 
	}
	
	public HistoryService getHistoryService(){
		return this.historyService;
	}

	protected abstract void copyFormProperties(Map<String, String> destination);

	protected abstract void populateFormField(String propertyId,
			String propertyValue);
	
	protected abstract void populateFormInit(String taskId, String executionId);

	@Override
	public Component getFormComponent() {
		return this;
	}

}
