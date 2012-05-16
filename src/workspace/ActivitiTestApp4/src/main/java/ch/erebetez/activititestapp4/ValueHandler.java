package ch.erebetez.activititestapp4;

import java.util.Locale;

import com.vaadin.Application;

public class ValueHandler {

    private static ValueHandler instance = null;
	
    private Activititestapp4 application;
	
	public static ValueHandler instance(){
		if(instance == null){
			instance = new ValueHandler();
		}
		return instance;
	}
	
	public String getUser() {
		return (String) application.getUser();
	}
	
	public Locale getLocale() {
		return application.getLocale();
	}
	

	public void setApplication(Activititestapp4 application) {
		this.application = application;
	}

	
	public void logoutUser(){
		application.logout();
	}
	
	private ValueHandler(){
		
	}
	
	
}
