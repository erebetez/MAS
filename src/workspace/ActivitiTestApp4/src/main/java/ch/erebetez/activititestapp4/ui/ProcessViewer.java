package ch.erebetez.activititestapp4.ui;



import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;


@Configurable(preConstruction = true)
public class ProcessViewer extends CustomComponent{
	private static final long serialVersionUID = 7604893734442405078L;

	private static Logger log = Logger.getLogger(ProcessViewer.class
			.getName());	
	
	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected RuntimeService runtimeservice;

	
	Table processTable = null;

	Button startNewInstanceButton = null;
	
	
	ProcessDefinition processDefinition;
	
	public Button getStartNewInstanceButton() {
		if(startNewInstanceButton == null){
	    	startNewInstanceButton = new Button("Start Process");
	    	startNewInstanceButton.setEnabled(false);
	    	
	 		startNewInstanceButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -6441664988506039946L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					startNewInstance(processDefinition);
				}
			});			

		}
		
		return startNewInstanceButton;
	}
	
	
	public void setButtonCaption(String caption){
		getStartNewInstanceButton().setCaption(caption);
		getStartNewInstanceButton().setEnabled(true);
	}

	public Table getprocessTable() {
		if(processTable == null){
		   processTable = new Table();
		   processTable.setSizeFull();
	        
	        // Allow selecting items from the table.
	        processTable.setSelectable(true);

	        // Send changes in selection immediately to server.
	        processTable.setImmediate(true);
	        
	        processTable.addListener(new ItemClickListener() {
	        	
				private static final long serialVersionUID = 1L;

				public void itemClick(ItemClickEvent event) {
	                if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
	                	
	                	processDefinition = (ProcessDefinition) event.getItemId();

	                	setButtonCaption("Start process " + processDefinition.getName() );
	                    System.out.println("Hallo................." + processDefinition.toString() );
	                    
	                }

	            }


	        });

		}
		return processTable;
	}

	

	public ProcessViewer(){
		Panel panel = new Panel("Processes");
		panel.setContent(new VerticalLayout());

    	
    	panel.addComponent(getStartNewInstanceButton());
		panel.addComponent(getprocessTable());  	
		
		
        // The composition root MUST be set
        setCompositionRoot(panel);
        
        populateProcessTable();
  
        
	}

	
	private void populateProcessTable(){

		List<ProcessDefinition> definitionList = repositoryService
			.createProcessDefinitionQuery()
			.latestVersion().list();
		
		BeanItemContainer<ProcessDefinition> dataSource = new BeanItemContainer<ProcessDefinition>(
				ProcessDefinition.class, definitionList);
		
		processTable.setContainerDataSource(dataSource);
		processTable.setVisibleColumns(new String[] { "name", "version" });

//		processTable.setVisibleColumns(new String[] { "name", "key", "version",
//				"resourceName", "category"  });

	}
	
	
	
	
	
	public void startNewInstance(ProcessDefinition processDefinition) {
		log.log(Level.INFO, "Starting instance of process {1}",
				processDefinition.getKey());
		try {
			//TODO implement starting forms
//			if (processDefinitionHasForm(processDefinition)) {
//				openFormForProcessDefinition(processDefinition);
//			} else {
			runtimeservice.startProcessInstanceById(
						processDefinition.getId());
				showProcessStartSuccess(processDefinition);
//			}
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not start process instance", e);
			showProcessStartFailure(processDefinition);
		}
	}	
	
	public void showProcessStartSuccess(ProcessDefinition process) {
		getWindow().showNotification(
				String.format("%s started successfully", process.getName()),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}
	
	public void showProcessStartFailure(ProcessDefinition process) {
		getWindow().showNotification(
						String.format(
								"Could not start %s. Please check the logs for more information.",
								process.getName()),
						Notification.TYPE_ERROR_MESSAGE);
	}	
//    @Override
//    public void attach() {
//    	super.attach(); // Must call.
//  	
//    }
    

	
}
