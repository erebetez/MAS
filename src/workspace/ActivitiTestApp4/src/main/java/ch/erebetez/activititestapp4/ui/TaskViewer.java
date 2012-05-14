package ch.erebetez.activititestapp4.ui;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;


@Configurable(preConstruction = true)
public class TaskViewer extends CustomComponent {

	private static final long serialVersionUID = 7765912131071411327L;

	@Autowired
	TaskService taskService;
	
	Table taskTabel = new Table();
	
	public TaskViewer(){
		Panel panel = new Panel("Tasks");
		panel.setContent(new VerticalLayout());

		
		panel.addComponent(taskTabel);  	
				
        // The composition root MUST be set
        setCompositionRoot(panel);
        
        populateProcessTable();
        
        taskTabel.setSizeFull();
        
        taskTabel.setSelectable(true);
	}
	
	
	private void populateProcessTable(){
		
        TaskQuery query = taskService.createTaskQuery();
        
        // FIXME Userawardness... .taskCandidateUser("admin")
        List<Task> taskList  = query.taskUnnassigned()
		.orderByTaskPriority().desc().orderByDueDate().desc().list();

		
		BeanItemContainer<Task> dataSource = new BeanItemContainer<Task>(
				Task.class, taskList);
		
		taskTabel.setContainerDataSource(dataSource);
		taskTabel.setVisibleColumns( new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime" });
	}
	

//String currentUser = getIdOfCurrentUser();

	
//protected String getIdOfCurrentUser() {
//	return (String) getApplication().getUser();
//}


}
