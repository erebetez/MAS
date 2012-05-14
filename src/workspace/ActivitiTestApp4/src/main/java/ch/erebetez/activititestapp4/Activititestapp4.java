package ch.erebetez.activititestapp4;


import ch.erebetez.activititestapp4.ui.*;

import com.vaadin.Application;
import com.vaadin.ui.*;


@SuppressWarnings("serial")

public class Activititestapp4 extends Application {


	private Window window = null;
	
	
	@Override
	public void init() {
		// setTheme("VaadinActivitiDemo");
		SetupInitialData setup = new SetupInitialData();
		setup.init();

		createAndShowLoginWindow();
	}

	private void createAndShowLoginWindow() {
        
		// TODO add login
		
		window = new LabDashboard();



		setMainWindow(window);
		
	
		
	}
	
	
//	private void populateTable() {
//		UserQuery query = identityService.createUserQuery();
//		List<User> allUsers = query.orderByUserId().asc().list();
//		BeanItemContainer<User> dataSource = new BeanItemContainer<User>(
//				User.class, allUsers);
//		userTable.setContainerDataSource(dataSource);
//		userTable.setVisibleColumns(new String[] { "id", "firstName",
//				"lastName", "email" });
//	}

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

//	 @Override
//	 public void handleViewEvent(ViewEvent event) {
//	 if (event instanceof UserLoggedInEvent) {
//	 String username = ((UserLoggedInEvent) event).getUsername();
//	 setUser(username);
//	 createAndShowMainWindow();
//	 } else if (event instanceof UserLoggedOutEvent) {
//	 close();
//	 }
//	 }

}
