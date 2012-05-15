package ch.erebetez.activititestapp4.ui;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

public class MainWindow extends Window  {
	private static final long serialVersionUID = 8659686516723461688L;
	
	private TabSheet tabsheet;
	private LabDashboard labDashboard;
	private ActivityWindow activitWindow;
	
	public MainWindow(){
		setCaption("Laboratory Execution");

        tabsheet = new TabSheet();
//        tabsheet.setHeight("200px");
//        tabsheet.setWidth("400px");

        labDashboard = new LabDashboard();
        activitWindow = new ActivityWindow();
        
        tabsheet.addTab(labDashboard, "Lab Dasboard");
        tabsheet.addTab(activitWindow, "My Activitys");

        addComponent(tabsheet);
//        tabsheet.addListener(this);
		
	}
	
	
}
