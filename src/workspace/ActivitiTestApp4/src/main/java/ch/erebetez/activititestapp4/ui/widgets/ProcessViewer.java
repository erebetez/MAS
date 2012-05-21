package ch.erebetez.activititestapp4.ui.widgets;



import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.I18nManager;
import ch.erebetez.activititestapp4.Messages;
import ch.erebetez.activititestapp4.ui.RefreshListener;

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
	
	@Autowired
	private I18nManager i18n;

	private List<RefreshListener> refreshListeners = new ArrayList<RefreshListener>();
	
	private Table processTable = null;

	private Button startNewInstanceButton = null;

	private ProcessDefinition processDefinition;
	
	
	public ProcessViewer(){
		Panel panel = new Panel(i18n.get(Messages.ACTIVIT_PROCESS));
		panel.setContent(new VerticalLayout());
		panel.setSizeFull();
    	
    	panel.addComponent(getStartNewInstanceButton());
		panel.addComponent(getprocessTable());  	
		
        setCompositionRoot(panel);
        
        populateProcessTable();
  
	}
	
	public Button getStartNewInstanceButton() {
		if(startNewInstanceButton == null){
	    	startNewInstanceButton = new Button(i18n.get(Messages.ACTIVIT_START_PROCESS));
	    	startNewInstanceButton.setEnabled(false);
	    	
	 		startNewInstanceButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -6441664988506039946L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					startNewInstance(processDefinition);
					// Signal the listeners
			        for (RefreshListener listener : refreshListeners) {
			            listener.refresh();
			        }	
				}
			});			

		}
		
		return startNewInstanceButton;
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
	                	getStartNewInstanceButton().setEnabled(true);
	                	processDefinition = (ProcessDefinition) event.getItemId();
	                }
	            }
	        });
		}
		return processTable;
	}


	private void populateProcessTable(){

		List<ProcessDefinition> definitionList = repositoryService
			.createProcessDefinitionQuery()
			.latestVersion().processDefinitionCategory("http://www.erebetez.ch/mainProcess").list();
		
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

	public void addListener(RefreshListener listener){
		refreshListeners.add(listener);
	}	

}
