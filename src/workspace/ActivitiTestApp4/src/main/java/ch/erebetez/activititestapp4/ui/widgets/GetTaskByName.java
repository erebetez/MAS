package ch.erebetez.activititestapp4.ui.widgets;


import java.util.*;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.I18nManager;
import ch.erebetez.activititestapp4.Messages;

import com.vaadin.data.Property;
import com.vaadin.ui.TextField;

@Configurable(preConstruction = true)
public class GetTaskByName extends TextField {
	private static final long serialVersionUID = 8173557303490394828L;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private I18nManager i18n;
	
	private List<ShowFormListener> showFormListeners = new ArrayList<ShowFormListener>();

	public GetTaskByName(){
        this.setCaption(i18n.get(Messages.GUI_INPUT_TASK_NAME));
	    this.setImmediate(true);
		this.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1006498268256748042L;

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				
				 selectByName((String) getValue());				
			}
		});
	}

	private void selectByName(String name){

        TaskQuery query = taskService.createTaskQuery();
        
        Task task  = query.taskAssignee(App.instance().user())
		   .taskName(name).singleResult();

        // TODO Show a message when more than two results are found. (.list())
        
        if(task == null){
        	getWindow().showNotification(name + " not Found!");
        	return;
        }
        
		for(ShowFormListener listener : showFormListeners){
			listener.showFormForTask(task);
		}
		
	    getWindow().showNotification(name);
	}

	public void addListener(ShowFormListener listener){
		showFormListeners.add(listener);
	}
	
}
