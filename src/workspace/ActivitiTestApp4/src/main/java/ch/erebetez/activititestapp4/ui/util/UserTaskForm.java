package ch.erebetez.activititestapp4.ui.util;

import java.util.Map;

import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;

import com.vaadin.ui.Component;

public interface UserTaskForm extends java.io.Serializable {

	enum Type {
		START_FORM, TASK_FORM
	}

	Type getFormType();

//	String getProcessDefinitionId();

	Task getTask();

	String getDisplayName();

	String getDescription();

	String getFormKey();

	void populateForm(TaskFormData formData, Task task);

//	void populateForm(TaskFormData formData, String taskId);

	Map<String, String> getFormProperties();

	Component getFormComponent();
}
