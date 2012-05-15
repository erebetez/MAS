package ch.erebetez.activititestapp4.ui.widgets;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)
public class MyTaskViewer extends CustomComponent{
	private static final long serialVersionUID = 3727005920258717798L;
	
	@Autowired
	private TaskService taskService;	

	private Table myTasksTable = null;
	
	private Button updateButton = null;
	
	private Task task;
	
	private FormViewer formViewer;
	
	public MyTaskViewer(FormViewer formViewer){
		this.formViewer = formViewer;
		Panel panel = new Panel("My Tasks");
		panel.setContent(new VerticalLayout());

		panel.addComponent(getUpdateButton());
		panel.addComponent(getmyTasksTable());  	

        setCompositionRoot(panel);
        
        populateTaskTable();
		
	}
	
	public Button getUpdateButton() {
		if(updateButton == null){
			updateButton = new Button("Refresh");
	    	
	    	updateButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -2546778565499238459L;

				@Override
				public void buttonClick(ClickEvent event) {
					populateTaskTable();
				}
			});
		}
		
		return updateButton;
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
		                	formViewer.showTaskForm(task.getId());
		                			                	
		                	
		                    System.out.println("Show................." + task.toString() );
		                    
		                }

		            }


		        });	       
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
