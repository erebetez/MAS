package ch.erebetez.activititestapp4.ui;

import java.util.ArrayList;
import java.util.List;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.ui.widgets.ProcessViewer;
import ch.erebetez.activititestapp4.ui.widgets.TaskViewer;


import com.vaadin.ui.*;


public class MainWindow extends VerticalLayout implements MenuListener {
	private static final long serialVersionUID = 8659686516723461688L;

	private MainMenuBar menubar = null;
	private ActivityWindow activitWindow;

	private List<RefreshListener> refreshListeners = new ArrayList<RefreshListener>();


	public MainWindow() {

		setMargin(false, true, true, true);
		setSpacing(true);

		MainTabSheet tabsheet = new MainTabSheet();

		activitWindow = new ActivityWindow();
		this.addListener(activitWindow.getMytaskViewer());

		tabsheet.addDashboardTab(getDashboard());
		tabsheet.addMyActivitysTab(activitWindow);
		tabsheet.addHistoryTab(new VerticalLayout());


		addComponent(getMenuBar());
		addComponent(tabsheet);

	}
	
	private Layout getDashboard(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		
		ProcessViewer processViewer = new ProcessViewer();
		TaskViewer taskViewer = new TaskViewer();

		this.addListener( taskViewer );

		layout.addComponent(processViewer);
		layout.addComponent(taskViewer);
		
		return layout;
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
		getWindow().showNotification("Refreshed all Tables");
	}

	
	public void addListener(RefreshListener listener) {
		this.refreshListeners.add(listener);
	}


	@Override
	public void menuEvent(MenuEventKey key) {
		
		switch(key){
		case RELOAD:
			refreshComponents();
			break;
		
		case LOGOUT:
			App.get().logoutUser();
			break;
		}

	}
	
	
}
