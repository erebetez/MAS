package ch.erebetez.activititestapp4.ui;

import ch.erebetez.activititestapp4.bpmn.forms.DilutionReportForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;
import ch.erebetez.activititestapp4.ui.widgets.*;

import com.vaadin.ui.*;

public class LabDashboard extends CustomComponent {
	private static final long serialVersionUID = -8080589786012261360L;
	
	private VerticalLayout layout = null;

	public LabDashboard(){
		
		setCaption("Laboratory Dashboard");
				
		layout = new VerticalLayout();
		layout.setSizeFull();
        setCompositionRoot(layout);	
		
		ProcessViewer processViewer = new ProcessViewer();
		TaskViewer taskViewer = new TaskViewer();
	
		
		layout.addComponent(processViewer);
		layout.addComponent(taskViewer);
		
	}
	

	
}
