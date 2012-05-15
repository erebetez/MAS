package ch.erebetez.activititestapp4.ui;

import ch.erebetez.activititestapp4.bpmn.forms.DilutionReportForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;
import ch.erebetez.activititestapp4.ui.widgets.FormViewer;
import ch.erebetez.activititestapp4.ui.widgets.MyTaskViewer;
import ch.erebetez.activititestapp4.ui.widgets.ProcessViewer;
import ch.erebetez.activititestapp4.ui.widgets.TaskViewer;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;

public class LabDashboard extends Window {

	private static final long serialVersionUID = -8080589786012261360L;
	
	private GridLayout layout = null;

	UserTaskFormContainer userTaskFormContainer;

	public LabDashboard(){
		
		setCaption("Laboratory Dashboard");
		
		
		layout = new GridLayout(3, 3);
		layout.setSizeFull();
		addComponent(layout);
		
		createAndInitUserTaskFormContainer();
		
		ProcessViewer processViewer = new ProcessViewer();
		TaskViewer taskViewer = new TaskViewer();
		
		FormViewer formViewer = new FormViewer(userTaskFormContainer);
		
		MyTaskViewer mytaskViewer = new MyTaskViewer(formViewer);
		
		
		
		layout.addComponent(processViewer, 0, 0);
		layout.addComponent(taskViewer, 1, 0);
		layout.addComponent(mytaskViewer, 2, 0);
		layout.addComponent(formViewer, 2, 1);
		
		
		
	}
	
	private void createAndInitUserTaskFormContainer() {
		userTaskFormContainer = new UserTaskFormContainer();
		userTaskFormContainer.registerForm(DilutionReportForm.FORM_KEY,
				DilutionReportForm.class);

	}
	
}
