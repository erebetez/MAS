package ch.erebetez.activititestapp4.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.ValueHandler;
import ch.erebetez.activititestapp4.ui.RefreshListener;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@Configurable(preConstruction = true)
public class MyTaskViewer extends CustomComponent implements RefreshListener{
	private static final long serialVersionUID = 3727005920258717798L;
	
	@Autowired
	private TaskService taskService;	

	private Table myTasksTable = null;
	
	private Task task;
	
	private List<ShowFormListener> showFormListeners = new ArrayList<ShowFormListener>();

	public MyTaskViewer(){
		Panel panel = new Panel("My Tasks");
		panel.setContent(new VerticalLayout());

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

	       
	       myTasksTable.addListener(new ItemClickListener() {	        	
					private static final long serialVersionUID = 1L;

					public void itemClick(ItemClickEvent event) {
		                if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
		                	
		                	task = (Task) event.getItemId();
		                	
		            		for(ShowFormListener listener : showFormListeners){
		            			listener.showFormForTask(task);
		            		}

		                    System.out.println("Show................." + task.toString() );
		                }
		            }
		        });	       
		}
		return myTasksTable;
	}	
	
	
	private void populateTaskTable(){
		
        TaskQuery query = taskService.createTaskQuery();
        
        List<Task> taskList  = query.taskAssignee(ValueHandler.instance().getUser())
		    .orderByTaskPriority().desc().orderByDueDate().desc().list();

		
		BeanItemContainer<Task> dataSource = new BeanItemContainer<Task>(
				Task.class, taskList);
		
		getmyTasksTable().setContainerDataSource(dataSource);
		getmyTasksTable().setVisibleColumns( new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime" });
	}


	public void addListener(ShowFormListener listener){
		showFormListeners.add(listener);
	}
	
	@Override
	public void refresh() {
		populateTaskTable();
	}
	
}
