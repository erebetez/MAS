package ch.erebetez.activititestapp4;

import ch.erebetez.activititestapp4.ui.*;
import ch.erebetez.activititestapp4.ui.login.LoginListener;
import ch.erebetez.activititestapp4.ui.login.LoginWindow;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class Activititestapp4 extends Application {
	private static final long serialVersionUID = 8718633567934963964L;

	private Window baseWindow = new Window();

	protected I18nManager i18nManager;

	private MainWindow mainWindow = null;
	private LoginWindow loginWindow = null;

	@Override
	public void init() {
		// setTheme("VaadinActivitiDemo");
		SetupInitialData setup = new SetupInitialData();
		setup.init();

		App.instance().setApplication(this);

        baseWindow.setCaption(App.instance().i18n(Messages.APP_TITLE));
		baseWindow.setBorder(Window.BORDER_DEFAULT);
		setMainWindow(baseWindow);

		showLoginWindow();
	}

	public void showLoginWindow() {
		if (loginWindow == null) {
			loginWindow = new LoginWindow();
			loginWindow.addListener(new LoginListener() {
				@Override
				public void logginSucess(String user) {
					
					if(user != null){
						setUser(user);
						baseWindow.setContent(getMyMainWindow());
					} else {
						baseWindow.setContent(loginWindow);
					}
				}
			});
		}
		baseWindow.setContent(loginWindow);

	}

	public VerticalLayout getMyMainWindow() {
		System.out.println("User: " + this.getUser());

		if (this.getUser() == null) {
			showLoginWindow();
			return null;
		}

		if (mainWindow == null) {
			mainWindow = new MainWindow();
		}

		return mainWindow;
	}

	

    public I18nManager getI18nManager() {
	    if( i18nManager == null){
	    	i18nManager = new I18nManager();
	    }
    	return i18nManager;
	}
    
    public void setI18nManager(I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }
	
	public void logout(){
		setUser(null);
		loginWindow.logout();
	}
	
	@Override
	public void close() {
		super.close();
	}


}
