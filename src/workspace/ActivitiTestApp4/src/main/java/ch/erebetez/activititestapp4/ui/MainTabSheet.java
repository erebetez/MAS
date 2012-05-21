package ch.erebetez.activititestapp4.ui;

import org.activiti.engine.task.TaskQuery;

import ch.erebetez.activititestapp4.App;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;

public class MainTabSheet extends TabSheet {
	private static final long serialVersionUID = 1L;

	private static final ThemeResource noteIcon = new ThemeResource(
			"../runo/icons/32/note.png");
	private static final ThemeResource userIcon = new ThemeResource(			
			"../runo/icons/32/user.png");
	private static final ThemeResource calendarIcon = new ThemeResource(
			"../runo/icons/32/calendar.png");


	public MainTabSheet() {
		this.setSizeFull();
	}

	public void addDashboardTab(Layout dashboard){
		this.addTab(dashboard, "Lab Dasboard", noteIcon);	
	}

	public void addMyActivitysTab(Layout myActivitys){
		this.addTab(myActivitys, "My Activitys", userIcon);
	}
	
	public void addHistoryTab(Layout history){
		this.addTab(history, "History", calendarIcon);	
	}
	
//	private long getNumberOfUnassignedTasks() {
//		String currentUser = App.instance().user();
//		TaskQuery query = taskService.createTaskQuery();
//		return query.taskUnnassigned().taskCandidateUser(currentUser).count();
//	}
//
//	private long getNumberOfTasksAssignedToCurrentUser() {
//		String currentUser = App.instance().user();
//		TaskQuery query = taskService.createTaskQuery();
//		return query.taskAssignee(currentUser).count();
//	}
	
}
