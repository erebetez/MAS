package ch.erebetez.activititestapp4.ui;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;

public class LabDashboard extends Window {

	private static final long serialVersionUID = -8080589786012261360L;
	
	private GridLayout layout = null;

	

	public LabDashboard(){
		
		setCaption("Laboratory Dashboard");
		
		
		layout = new GridLayout(3, 3);
		layout.setSizeFull();
		addComponent(layout);
		
		
		ProcessViewer processViewer = new ProcessViewer();
		TaskViewer taskViewer = new TaskViewer();
		
		
		
		layout.addComponent(processViewer, 0, 0);
		layout.addComponent(taskViewer, 0, 1);
		
		
	}
	

	
}
