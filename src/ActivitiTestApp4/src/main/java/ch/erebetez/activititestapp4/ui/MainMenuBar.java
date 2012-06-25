package ch.erebetez.activititestapp4.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.I18nManager;
import ch.erebetez.activititestapp4.Messages;
import ch.erebetez.activititestapp4.ui.MenuListener.MenuEventKey;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.MenuBar;


@Configurable(preConstruction = true)
public class MainMenuBar extends MenuBar{
	private static final long serialVersionUID = 3041598111655030327L;
	
	@Autowired
	private I18nManager i18n;
	
	private static final ThemeResource reloadIcon = new ThemeResource(
			"../runo/icons/16/reload.png");
	private static final ThemeResource lockIcon = new ThemeResource(
			"../runo/icons/16/lock.png");	
	private static final ThemeResource offlineIcon = new ThemeResource(
			"../runo/icons/16/globe.png");	
	
	private List<MenuListener> refreshListeners = new ArrayList<MenuListener>();	
	
	private MenuBar.MenuItem logout;
	
	public MainMenuBar() {		
		
		@SuppressWarnings("unused")
		final MenuBar.MenuItem refresh = addItem(i18n.get(Messages.REFRESH),
				reloadIcon, refreshCommand);

		@SuppressWarnings("unused")
		final MenuBar.MenuItem offlinemode = addItem(i18n.get(Messages.OFFLINE_MODE),
				offlineIcon, refreshCommand);
				
		logout = addItem(i18n.get(Messages.LOGOUT),
				lockIcon, logoutCommand);
	}	
	
	public void setUser(String user){
		// FIXME somhow the user is not updated..
		logout.setText(i18n.get(Messages.LOGOUT) + " (" + user + ")");
		this.requestRepaint();		
	}
	
	private Command refreshCommand = new Command() {
		private static final long serialVersionUID = 1443125922710133298L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
            for (MenuListener listener : refreshListeners) {
                listener.menuEvent(MenuEventKey.RELOAD);
            }           
		}
	};

	
	private Command logoutCommand = new Command() {
		private static final long serialVersionUID = 7337147243184703259L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
            for (MenuListener listener : refreshListeners) {
                listener.menuEvent(MenuEventKey.LOGOUT);
            }
		}
	};
	
	
	public void addListener(MenuListener listener){
		refreshListeners.add(listener);
	}
	
}
