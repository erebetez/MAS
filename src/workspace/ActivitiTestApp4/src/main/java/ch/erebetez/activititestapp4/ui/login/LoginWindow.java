package ch.erebetez.activititestapp4.ui.login;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


import com.vaadin.ui.*;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Window.Notification;

@Configurable(preConstruction = true)
public class LoginWindow extends VerticalLayout{
	private static final long serialVersionUID = -2459727691241360040L;

	@Autowired
	protected IdentityService identityervice;		
	
	LoginForm login = null;
	
    private List<LoginListener> listeners = new ArrayList<LoginListener>();

	
	public LoginWindow(){
		setMargin(true);
	    addComponent(getLoginForm());
	}
	
	private LoginForm getLoginForm(){
		if(login == null){
			login = new LoginForm();	
			
		    login.addListener(new LoginForm.LoginListener() {
				private static final long serialVersionUID = 6867079190478454445L;

				@Override
		    	public void onLogin(LoginEvent event) {
					attemptLogin(event.getLoginParameter("username"), event.getLoginParameter("password"));
		        }

		    });			
		}
		 
		return login;
	}
	
	
	private void attemptLogin(String username, String password) {
		if (identityervice.checkPassword(username, password)) {
			identityervice.setAuthenticatedUserId(username);
			getApplication().setUser(username);
			showLoginOk();
			
            for (LoginListener listener : listeners) {
                listener.logginSucess(username);
            }

		} else {
			showLoginFailed();
		}
	}
	

	private void showLoginFailed() {
		getWindow().showNotification(
				"Login failed. Please try again.",
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	private void showLoginOk() {
		getWindow().showNotification(
				"Login Ok. " + getApplication().getUser(),
				Notification.TYPE_HUMANIZED_MESSAGE);
	}

	public void addListener(LoginListener listener) {
		this.listeners.add(listener);  		
	}

}
