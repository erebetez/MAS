package ch.erebetez.activititestapp4.ui;



import com.vaadin.ui.*;



public class ProcessViewer extends CustomComponent{

	private static final long serialVersionUID = 7604893734442405078L;
	
	private VerticalLayout layout = null;
	
	public ProcessViewer(){
    	
		layout = new VerticalLayout();
		
		Label title = new Label("Processes");
		Label title2 = new Label("Processes2");
		
		Table processTabel = new Table();
		
		
		layout.addComponent(title);
		layout.addComponent(title2);
		layout.addComponent(processTabel);  	
		
		
        // The composition root MUST be set
        setCompositionRoot(layout);
	}
	
//    @Override
//    public void attach() {
//    	super.attach(); // Must call.
//  	
//    }
    

	
}
