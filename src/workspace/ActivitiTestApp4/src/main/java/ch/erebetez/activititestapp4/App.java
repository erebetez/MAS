package ch.erebetez.activititestapp4;

import java.util.Locale;

public class App {

    private static App instance = null;
	
    private Activititestapp4 application;
	
	public static App get(){
		if(instance == null){
			instance = new App();
		}
		return instance;
	}
	
	public String user() {
		return (String) application.getUser();
	}
	
	public Locale locale() {
		return application.getLocale();
	}
	
	public String i18n(String key){
		return application.getI18nManager().getMessage(key);
	}
	
	public String i18n(String key, Object... arguments){
		return application.getI18nManager().getMessage(key, arguments);
	}	
	

	public void setApplication(Activititestapp4 application) {
		this.application = application;
	}

	
	public void logoutUser(){
		application.logout();
	}
	

	
	private App(){
		
	}
	
	
}
