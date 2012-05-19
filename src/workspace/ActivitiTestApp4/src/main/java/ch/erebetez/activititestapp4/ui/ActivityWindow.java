package ch.erebetez.activititestapp4.ui;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;

import ch.erebetez.activititestapp4.bpmn.forms.*;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;
import ch.erebetez.activititestapp4.ui.widgets.FormViewer;
import ch.erebetez.activititestapp4.ui.widgets.GetTaskByName;
import ch.erebetez.activititestapp4.ui.widgets.MyTaskViewer;
import ch.erebetez.activititestapp4.ui.widgets.ShowFormListener;

import com.vaadin.ui.*;

public class ActivityWindow extends CustomComponent implements ShowFormListener{
	private static final long serialVersionUID = -7213851977742853381L;


	private FormViewer formViewer = null;
	private MyTaskViewer mytaskViewer = null;
	private GetTaskByName getTaskByNameLineInput = null;
	private UserTaskFormContainer userTaskFormContainer = null;
	
//	private RefreshListener refreshListener;

	private List<ShowFormListener> showFormListeners = new ArrayList<ShowFormListener>();
	
	
	public ActivityWindow() {
		setCaption("My Activitys");

		VerticalLayout taskLayout = new VerticalLayout();

		taskLayout.addComponent(getTaskByName());
		taskLayout.addComponent(getMytaskViewer());
		
		
		HorizontalLayout layout = new HorizontalLayout();
		
		layout.setSpacing(true);
		
		layout.addComponent(taskLayout);
		layout.addComponent(getFormViewer());

		setCompositionRoot(layout);
	}

	
	public FormViewer getFormViewer() {
		if (formViewer == null) {
			formViewer = new FormViewer(getUserTaskFormContainer());
			showFormListeners.add(formViewer);
		}
		return formViewer;
	}	
	
	public MyTaskViewer getMytaskViewer() {
		if (mytaskViewer == null) {
			mytaskViewer = new MyTaskViewer();
			mytaskViewer.addListener(this);
			getFormViewer().addListener(mytaskViewer);
		}
		return mytaskViewer;
	}
	
	public GetTaskByName getTaskByName() {
		if (getTaskByNameLineInput == null) {
			getTaskByNameLineInput = new GetTaskByName();
			getTaskByNameLineInput.addListener(this);
		}
		return getTaskByNameLineInput;
	}

	// FIXME userTaskContainer does not belong here!
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


	@Override
	public void showFormForTask(Task task) {
		for(ShowFormListener listener : showFormListeners){
			listener.showFormForTask(task);
		}
		
	}

}
