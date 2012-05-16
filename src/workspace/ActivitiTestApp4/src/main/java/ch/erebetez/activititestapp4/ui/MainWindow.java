package ch.erebetez.activititestapp4.ui;

import ch.erebetez.activititestapp4.ValueHandler;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class MainWindow extends VerticalLayout  {
	private static final long serialVersionUID = 8659686516723461688L;
	
    private MenuBar menubar = null;
	private TabSheet tabsheet;
	private LabDashboard labDashboard;
	private ActivityWindow activitWindow;
	
    private static final ThemeResource noteIcon = new ThemeResource(
            "../runo/icons/32/note.png");
    private static final ThemeResource userIcon = new ThemeResource(
            "../runo/icons/32/user.png");
    private static final ThemeResource reloadIcon = new ThemeResource(
            "../runo/icons/16/reload.png");
    private static final ThemeResource lockIcon = new ThemeResource(
            "../runo/icons/16/lock.png");

	
	public MainWindow(){
//		setCaption("Laboratory Execution");   		
		setMargin(false, true, true, true);
		setSpacing(true);
		
        tabsheet = new TabSheet();

        labDashboard = new LabDashboard();
        activitWindow = new ActivityWindow();
        
        tabsheet.addTab(labDashboard, "Lab Dasboard", noteIcon);
        tabsheet.addTab(activitWindow, "My Activitys", userIcon);
//        tabsheet.addListener(this);

        addComponent(getMenuBar());
        addComponent(tabsheet);
		
	}
	
	private MenuBar getMenuBar(){
		 if( menubar == null ){
			 menubar = new MenuBar();

			 String user =ValueHandler.instance().getUser();
			 
			 final MenuBar.MenuItem refresh = menubar.addItem("Refresh", reloadIcon, refreshCommand);
			 
			 final MenuBar.MenuItem logout = menubar.addItem("Logout " + user, lockIcon, logoutCommand);
			 
		 }
		 return menubar;
	}
	
   private Command refreshCommand = new Command() {
		private static final long serialVersionUID = 1443125922710133298L;
	
		@Override
	    public void menuSelected(MenuItem selectedItem) {
            getWindow().showNotification("Action " + selectedItem.getText());
        }
	};

	   private Command logoutCommand = new Command() {
		private static final long serialVersionUID = 7337147243184703259L;

			@Override
		    public void menuSelected(MenuItem selectedItem) {
	            getWindow().showNotification("Action " + selectedItem.getText());
	        }
		};	
	
}
