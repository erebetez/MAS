package ch.erebetez.activititestapp4.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public abstract class CustomComponentLabWindow extends CustomComponent{
	private static final long serialVersionUID = 1L;

	protected VerticalLayout layout;
	
	private Button refreshButton = null;
	
	public CustomComponentLabWindow(){
		layout = new VerticalLayout();
		layout.setSizeFull();
		setCompositionRoot(layout);
		
		layout.addComponent(getrefreshButton());
		
	}
	
	
	public Button getrefreshButton() {
		if(refreshButton == null){
			refreshButton = new Button("Refresh");
	    	
	    	refreshButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -2546778565499238459L;

				@Override
				public void buttonClick(ClickEvent event) {
//					populateTaskTable();
					System.out.println("click.....");
				}

			});

		}
		
		return refreshButton;
	}
}
