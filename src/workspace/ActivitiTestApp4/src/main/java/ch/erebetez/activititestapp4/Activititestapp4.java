package ch.erebetez.activititestapp4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.ui.*;
import ch.erebetez.activititestapp4.ui.login.LoginListener;
import ch.erebetez.activititestapp4.ui.login.LoginWindow;

import com.vaadin.Application;
import com.vaadin.ui.*;

@Configurable(preConstruction = true)
public class Activititestapp4 extends Application {
	private static final long serialVersionUID = 8718633567934963964L;

	private Window baseWindow = new Window();

	@Autowired
	private I18nManager i18n;

	private MainWindow mainWindow = null;
	private LoginWindow loginWindow = null;

	@Override
	public void init() {
		// setTheme("VaadinActivitiDemo");
		SetupInitialData setup = new SetupInitialData();
		setup.init();

		App.instance().setApplication(this);

        baseWindow.setCaption(i18n.get(Messages.APP_TITLE));
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

	public MainWindow getMyMainWindow() {
		System.out.println("User: " + this.getUser());

		if (this.getUser() == null) {
			loginWindow.logout();
//			showLoginWindow();
			return null;
		}

		if (mainWindow == null) {
			mainWindow = new MainWindow();
		}

		return mainWindow;
	}

	public void logout(){
		this.setUser(null);		
		loginWindow.logout();
	}
	
	@Override
	public void close() {
		super.close();
	}


}
