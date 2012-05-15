package ch.erebetez.activititestapp4.ui;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@Configurable(preConstruction = true)
public class MyTaskViewer extends CustomComponent{
	private static final long serialVersionUID = 3727005920258717798L;

	Table myTasksTable = null;
	
	@Autowired
	TaskService taskService;	
	
	public MyTaskViewer(){
		Panel panel = new Panel("My Tasks");
		panel.setContent(new VerticalLayout());

//		panel.addComponent(getclaimTaskButton());
		panel.addComponent(getmyTasksTable());  	

        setCompositionRoot(panel);
        
        populateTaskTable();
		
	}
	
	public Table getmyTasksTable() {
		if(myTasksTable == null){
		   myTasksTable = new Table();
		   myTasksTable.setSizeFull();
	        
	       // Allow selecting items from the table.
	       myTasksTable.setSelectable(true);

		}
		return myTasksTable;
	}	
	
	
	private void populateTaskTable(){
		
        TaskQuery query = taskService.createTaskQuery();
        
        // FIXME Userawardness... 
        List<Task> taskList  = query.taskAssignee("admin")
		.orderByTaskPriority().desc().orderByDueDate().desc().list();

		
		BeanItemContainer<Task> dataSource = new BeanItemContainer<Task>(
				Task.class, taskList);
		
		getmyTasksTable().setContainerDataSource(dataSource);
		getmyTasksTable().setVisibleColumns( new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime" });
	}
}
