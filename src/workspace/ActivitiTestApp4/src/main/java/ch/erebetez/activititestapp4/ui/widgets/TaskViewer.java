package ch.erebetez.activititestapp4.ui.widgets;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;


@Configurable(preConstruction = true)
public class TaskViewer extends CustomComponent implements RefreshListener{
	private static final long serialVersionUID = 7765912131071411327L;

	private static Logger log = Logger.getLogger(ProcessViewer.class
			.getName());	
	
	@Autowired
	TaskService taskService;
	
	Table taskTable = null;
	
	Button claimTaskButton = null;

	Button updateButton = null;
	
	Task task;
	
	
	public TaskViewer(){
		Panel panel = new Panel("Tasks");
		panel.setContent(new VerticalLayout());

		panel.addComponent(getclaimTaskButton());
		panel.addComponent(gettaskTable());  	
				
        // The composition root MUST be set
        setCompositionRoot(panel);
        
        populateTaskTable();
        
	}
	
	public Button getclaimTaskButton() {
		if(claimTaskButton == null){
	    	claimTaskButton = new Button("Claim Taks");
	    	claimTaskButton.setEnabled(false);
	    	
	 		claimTaskButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -6441664988506039946L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					assignTaskToCurrentUser(task);
				}
			});			
		}
		
		return claimTaskButton;
	}
	
	public void setButtonCaption(String caption){
		getclaimTaskButton().setCaption(caption);
		getclaimTaskButton().setEnabled(true);
	}	
	
	public Table gettaskTable() {
		if(taskTable == null){
			taskTable = new Table();
		    taskTable.setSizeFull();
	        
	        // Allow selecting items from the table.
	        taskTable.setSelectable(true);

	        // Send changes in selection immediately to server.
	        taskTable.setImmediate(true);
	        
	        taskTable.addListener(new ItemClickListener() {	        	
				private static final long serialVersionUID = 1L;

				public void itemClick(ItemClickEvent event) {
	                if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
	                	
	                	task = (Task) event.getItemId();

	                	setButtonCaption("Claim task " + task.getName() );
	                    System.out.println("Hallo................." + task.toString() );
	                    
	                }
	            }
	        });

		}
		return taskTable;
	}


	
	private void populateTaskTable(){
		
        TaskQuery query = taskService.createTaskQuery();
        
        List<Task> taskList  = query.taskUnnassigned()
        		.taskCandidateUser(ValueHandler.instance().getUser())
		.orderByTaskPriority().desc().orderByDueDate().desc().list();

		
		BeanItemContainer<Task> dataSource = new BeanItemContainer<Task>(
				Task.class, taskList);
		
		gettaskTable().setContainerDataSource(dataSource);
		gettaskTable().setVisibleColumns( new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime" });
	}
	
	
	
	public void assignTaskToCurrentUser(Task task) {
		String currentUserId = ValueHandler.instance().getUser();

//		log.log(Level.INFO, "Assigning task {1} to user {2}", new Object[] {
//				task.getId(), currentUserId });
		try {
			taskService.claim(task.getId(), currentUserId);
			
			populateTaskTable();
			showTaskAssignmentSuccess(task);
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not assign task to user", e);
			showTaskAssignmentFailure(task);
		}
	}
	

	public void showTaskAssignmentSuccess(Task task) {
		getWindow().showNotification(
				String.format("%s assigned successfully", task.getName()),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}


	public void showTaskAssignmentFailure(Task task) {
		getWindow().showNotification(
						String.format(
								"Could not assign %s. Please check the logs for more information.",
								task.getName()),
						Notification.TYPE_ERROR_MESSAGE);
	}


	@Override
	public void refresh() {
		populateTaskTable();
	}


}
