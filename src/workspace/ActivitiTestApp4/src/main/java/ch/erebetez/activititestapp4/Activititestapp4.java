package ch.erebetez.activititestapp4;


import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.ProcessDefinition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.ui.*;

import com.vaadin.Application;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Configurable(preConstruction = true)
public class Activititestapp4 extends Application {

	@Autowired
	protected RepositoryService repositoryService;

    @Autowired
    protected IdentityService identityService;

	private Window window = null;

	private GridLayout layout = null;

	private Table userTable;
	
	private ProcessViewer processViewer = null;
	
	
	@Override
	public void init() {
		// setTheme("VaadinActivitiDemo");
		SetupInitialData setup = new SetupInitialData();
		setup.init();

		createAndShowLoginWindow();
	}

	private void createAndShowLoginWindow() {

		window = new Window("My Vaadin Application");

		layout = new GridLayout(3, 5);
		window.addComponent(layout);

		setMainWindow(window);

		Button button = new Button("Click Me");
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"), 2, 0);
			}
		});
		
		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("easyProcess").latestVersion().singleResult();
		

		
		layout.addComponent(new Label("Hello ...! " + def.getName()), 0, 0);
		layout.addComponent(button, 0, 1);

		userTable = new Table();
		layout.addComponent(userTable, 0, 2);
		
		
		processViewer = new ProcessViewer();
		layout.addComponent(processViewer, 1, 0);
		
		
		populateTable();
		
		
	}
	
	
	private void populateTable() {
		UserQuery query = identityService.createUserQuery();
		List<User> allUsers = query.orderByUserId().asc().list();
		BeanItemContainer<User> dataSource = new BeanItemContainer<User>(
				User.class, allUsers);
		userTable.setContainerDataSource(dataSource);
		userTable.setVisibleColumns(new String[] { "id", "firstName",
				"lastName", "email" });
	}

	// private void createAndShowMainWindow() {
	//
	// }
	//
	// private void createAndInitViewProvider() {
	//
	// }
	//
	// private void createAndInitUserTaskFormContainer() {
	//
	// }

	@Override
	public void close() {
		// ProcessEngines.getDefaultProcessEngine().getIdentityService()
		// .setAuthenticatedUserId(null);
		super.close();
	}

	// @Override
	// public void handleViewEvent(ViewEvent event) {
	// if (event instanceof UserLoggedInEvent) {
	// String username = ((UserLoggedInEvent) event).getUsername();
	// setUser(username);
	// createAndShowMainWindow();
	// } else if (event instanceof UserLoggedOutEvent) {
	// close();
	// }
	// }

}
