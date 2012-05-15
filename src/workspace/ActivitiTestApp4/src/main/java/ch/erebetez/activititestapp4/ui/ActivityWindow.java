package ch.erebetez.activititestapp4.ui;




import ch.erebetez.activititestapp4.bpmn.forms.DilutionReportForm;
import ch.erebetez.activititestapp4.ui.util.UserTaskFormContainer;
import ch.erebetez.activititestapp4.ui.widgets.FormViewer;
import ch.erebetez.activititestapp4.ui.widgets.MyTaskViewer;

import com.vaadin.ui.*;

public class ActivityWindow extends CustomComponent{
	private static final long serialVersionUID = -7213851977742853381L;

	UserTaskFormContainer userTaskFormContainer;
	
	public ActivityWindow(){
		
		setCaption("My Activiys");
				
		VerticalLayout layout = new VerticalLayout();		
		setCompositionRoot(layout);
		 
        layout.addComponent(new Label("Select a task to activate the Form"));				
		
        
		FormViewer formViewer = new FormViewer(getUserTaskFormContainer());
		
		MyTaskViewer mytaskViewer = new MyTaskViewer(formViewer);
				
		layout.addComponent(mytaskViewer);
		layout.addComponent(formViewer);
  
	}
	
	
	private UserTaskFormContainer getUserTaskFormContainer() {
		if(userTaskFormContainer == null) {
			userTaskFormContainer = new UserTaskFormContainer();
			userTaskFormContainer.registerForm(DilutionReportForm.FORM_KEY,
					DilutionReportForm.class);

		}
		return userTaskFormContainer;
	}
	
	
}
