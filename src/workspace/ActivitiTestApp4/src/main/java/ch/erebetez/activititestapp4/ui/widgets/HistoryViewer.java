package ch.erebetez.activititestapp4.ui.widgets;

import org.activiti.engine.HistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.I18nManager;
import ch.erebetez.activititestapp4.Messages;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


@Configurable(preConstruction = true)
public class HistoryViewer extends CustomComponent{
	private static final long serialVersionUID = -6787908730426379040L;

	@Autowired
	protected HistoryService historyservice;
	
	@Autowired
	private I18nManager i18n;
	
	private VerticalLayout layout = new VerticalLayout();
	
	public HistoryViewer(){
		
		layout.setSizeFull();
    	
	    Label hello = new Label("TODO. Implement it.");
	    layout.addComponent(hello);
	    
        setCompositionRoot(layout);		
	}
	
	
	
}
