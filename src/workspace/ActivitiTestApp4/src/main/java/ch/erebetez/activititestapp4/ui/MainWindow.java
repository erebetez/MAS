package ch.erebetez.activititestapp4.ui;

import java.util.ArrayList;
import java.util.List;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.bpmn.forms.CheckInventoryReportForm;
import ch.erebetez.activititestapp4.bpmn.forms.CreateOosForm;
import ch.erebetez.activititestapp4.bpmn.forms.DilutionReportForm;
import ch.erebetez.activititestapp4.bpmn.forms.IsInventoryItemOkForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;
import ch.erebetez.activititestapp4.ui.widgets.FormViewer;
import ch.erebetez.activititestapp4.ui.widgets.GetTaskByName;
import ch.erebetez.activititestapp4.ui.widgets.MyTaskViewer;
import ch.erebetez.activititestapp4.ui.widgets.ProcessViewer;
import ch.erebetez.activititestapp4.ui.widgets.TaskViewer;


import com.vaadin.ui.*;


public class MainWindow extends VerticalLayout implements MenuListener {
	private static final long serialVersionUID = 8659686516723461688L;

	private MainMenuBar menubar = null;

	private FormViewer formViewer = null;
	private MyTaskViewer mytaskViewer = null;
	private GetTaskByName getTaskByNameLineInput = null;
	private UserTaskFormContainer userTaskFormContainer = null;
	private ProcessViewer processViewer = null;
    private TaskViewer taskViewer = null;	
	
	private List<RefreshListener> refreshListeners = new ArrayList<RefreshListener>();


	public MainWindow() {
		setMargin(false, true, true, true);
		setSpacing(true);

		MainTabSheet tabsheet = new MainTabSheet();

		tabsheet.addDashboardTab(getDashboard());
		tabsheet.addMyActivitysTab(getMyActivitys());
		tabsheet.addHistoryTab(new VerticalLayout());

		addComponent(getMenuBar());
		addComponent(tabsheet);
	}
	
	
	private Layout getDashboard(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);

		layout.addComponent(getProcessViewer() );
		layout.addComponent(getTaskViewer());
		
		return layout;
	}
	
	
	public ProcessViewer getProcessViewer() {
		if (processViewer == null) {
			processViewer = new ProcessViewer();
			processViewer.addListener( getTaskViewer() );
		}
		return processViewer;
	}
	
	
	public TaskViewer getTaskViewer() {
		if (taskViewer == null) {
			taskViewer = new TaskViewer();
			taskViewer.addListener( getMytaskViewer());
			getFormViewer().addListener(taskViewer);
			this.addListener(taskViewer);
		}
		return taskViewer;
	}


	private Layout getMyActivitys(){		
		VerticalLayout taskLayout = new VerticalLayout();
		taskLayout.addComponent(getTaskByName());
		taskLayout.addComponent(getMytaskViewer());

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		
		layout.addComponent(taskLayout);
		layout.addComponent(getFormViewer());	
		
		return layout;
	}
	
	public GetTaskByName getTaskByName() {
		if (getTaskByNameLineInput == null) {
			getTaskByNameLineInput = new GetTaskByName();
			getTaskByNameLineInput.addListener(getFormViewer());
		}
		return getTaskByNameLineInput;
	}
	
	
	public MyTaskViewer getMytaskViewer() {
		if (mytaskViewer == null) {
			mytaskViewer = new MyTaskViewer();
			mytaskViewer.addListener( getFormViewer() );
			getFormViewer().addListener(mytaskViewer);
			this.addListener(mytaskViewer);
		}
		return mytaskViewer;
	}	
	
	
	public FormViewer getFormViewer() {
		if (formViewer == null) {
			formViewer = new FormViewer(getUserTaskFormContainer());
			getMytaskViewer().addListener(formViewer);
			getTaskByName().addListener(formViewer);
		}
		return formViewer;
	}
	
	private UserTaskFormContainer getUserTaskFormContainer() {
		if (userTaskFormContainer == null) {			
			userTaskFormContainer = new UserTaskFormContainer();
			
			userTaskFormContainer.registerForm(DilutionReportForm.FORM_KEY,
					DilutionReportForm.class);
			userTaskFormContainer.registerForm(IsInventoryItemOkForm.FORM_KEY,
					IsInventoryItemOkForm.class);			
			userTaskFormContainer.registerForm(CheckInventoryReportForm.FORM_KEY,
					CheckInventoryReportForm.class);					
			userTaskFormContainer.registerForm(CreateOosForm.FORM_KEY,
					CreateOosForm.class);
		}
		return userTaskFormContainer;
	}
		
	

	private MainMenuBar getMenuBar() {
		if (menubar == null) {
			menubar = new MainMenuBar();
			menubar.addListener(this);
		}
		return menubar;
	}

	
	private void refreshComponents(){
		// TODO add some logic to reduce overhead.
		for (RefreshListener listener : refreshListeners) {
            listener.refresh();
        }
	}

	
	public void addListener(RefreshListener listener) {
		this.refreshListeners.add(listener);
	}


	@Override
	public void menuEvent(MenuEventKey key) {
		
		switch(key){
		case RELOAD:
			refreshComponents();
			getWindow().showNotification("Refreshed all Tables");
			break;
		
		case LOGOUT:
			App.get().logoutUser();
			break;
		}

	}
	
	
}
