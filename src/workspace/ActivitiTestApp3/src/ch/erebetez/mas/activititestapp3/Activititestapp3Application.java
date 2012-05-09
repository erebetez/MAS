package ch.erebetez.mas.activititestapp3;

import org.activiti.engine.RepositoryService;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;


@SuppressWarnings("serial")
@Configurable(preConstruction = true)
public class Activititestapp3Application extends Application {

    @Autowired
    protected RepositoryService repositoryService;
	
	private Window window = null;
	
	private VerticalLayout layout = null;
    
	@Override
	public void init() {
//		setTheme("VaadinActivitiDemo");
		SetupDemo.init();
		createAndShowLoginWindow();
	}

	private void createAndShowLoginWindow() {
	
	  window = new Window("My Vaadin Application");    
	  
	  layout = new VerticalLayout();
	  window.addComponent(layout);
	  
       setMainWindow(window);
       
       Button button = new Button("Click Me");
       button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
        });

       
//       ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
       
//       RuntimeService runtimeService = processEngine.getRuntimeService();
//       RepositoryService repositoryService = processEngine.getRepositoryService();
//       TaskService taskService = processEngine.getTaskService();
//       ManagementService managementService = processEngine.getManagementService();       
//       
//       IdentityService identityService = processEngine.getIdentityService();

	   ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("panRefereceSamples").singleResult();       
	   
       
       layout.addComponent(new Label("Hello ...! " + def.getName())); 
       layout.addComponent(button); 
       
       
	}	

	
	
//	private void createAndShowMainWindow() {
//
//	}
//
//	private void createAndInitViewProvider() {
//
//	}
//
//	private void createAndInitUserTaskFormContainer() {
//
//	}

	@Override
	public void close() {
//		ProcessEngines.getDefaultProcessEngine().getIdentityService()
//				.setAuthenticatedUserId(null);
		super.close();
	}

//	@Override
//	public void handleViewEvent(ViewEvent event) {
//		if (event instanceof UserLoggedInEvent) {
//			String username = ((UserLoggedInEvent) event).getUsername();
//			setUser(username);
//			createAndShowMainWindow();
//		} else if (event instanceof UserLoggedOutEvent) {
//			close();
//		}
//	}

}
