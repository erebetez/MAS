package ch.erebetez.activititestapp4.ui;

import ch.erebetez.activititestapp4.ui.widgets.*;

public class LabDashboard extends CustomComponentLabWindow {
	private static final long serialVersionUID = -8080589786012261360L;
	
	public LabDashboard(){		
		setCaption("Laboratory Dashboard");
		
		layout.addComponent(new ProcessViewer());
		layout.addComponent(new TaskViewer());
		
	}
	

	
}
