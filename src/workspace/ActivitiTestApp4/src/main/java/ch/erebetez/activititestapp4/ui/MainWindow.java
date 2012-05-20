package ch.erebetez.activititestapp4.ui;

import java.util.ArrayList;
import java.util.List;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.ui.login.LoginListener;
import ch.erebetez.activititestapp4.ui.widgets.ProcessViewer;
import ch.erebetez.activititestapp4.ui.widgets.TaskViewer;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class MainWindow extends VerticalLayout {
	private static final long serialVersionUID = 8659686516723461688L;

	private MenuBar menubar = null;
	private TabSheet tabsheet;
	private ActivityWindow activitWindow;

	private List<RefreshListener> refreshListeners = new ArrayList<RefreshListener>();

	private static final ThemeResource noteIcon = new ThemeResource(
			"../runo/icons/32/note.png");
	private static final ThemeResource userIcon = new ThemeResource(			
			"../runo/icons/32/user.png");
	private static final ThemeResource calendarIcon = new ThemeResource(
			"../runo/icons/32/calendar.png");	
	private static final ThemeResource reloadIcon = new ThemeResource(
			"../runo/icons/16/reload.png");
	private static final ThemeResource lockIcon = new ThemeResource(
			"../runo/icons/16/lock.png");

	public MainWindow() {
		// setCaption("Laboratory Execution");
		setMargin(false, true, true, true);
		setSpacing(true);

		tabsheet = new TabSheet();

		activitWindow = new ActivityWindow();
		this.addListener(activitWindow.getMytaskViewer());

		tabsheet.addTab(getDashboard(), "Lab Dasboard", noteIcon);
		tabsheet.addTab(activitWindow, "My Activitys", userIcon);
		tabsheet.addTab(new VerticalLayout(), "History", calendarIcon);
//		 tabsheet.addListener(this);

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

	private MenuBar getMenuBar() {
		if (menubar == null) {
			menubar = new MenuBar();

			String user = App.get().user();

			final MenuBar.MenuItem refresh = menubar.addItem("Refresh",
					reloadIcon, refreshCommand);

			final MenuBar.MenuItem logout = menubar.addItem("Logout " + user,
					lockIcon, logoutCommand);

		}
		return menubar;
	}

	private Command refreshCommand = new Command() {
		private static final long serialVersionUID = 1443125922710133298L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
            for (RefreshListener listener : refreshListeners) {
                listener.refresh();
            }
            getWindow().showNotification("Refreshed all Tables");
		}
	};

	private Command logoutCommand = new Command() {
		private static final long serialVersionUID = 7337147243184703259L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
			App.get().logoutUser();
		}
	};

	public void addListener(RefreshListener listener) {
		this.refreshListeners.add(listener);
	}

}
