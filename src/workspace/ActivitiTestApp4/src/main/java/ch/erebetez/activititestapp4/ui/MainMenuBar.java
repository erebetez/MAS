package ch.erebetez.activititestapp4.ui;

import java.util.ArrayList;
import java.util.List;

import ch.erebetez.activititestapp4.App;
import ch.erebetez.activititestapp4.ui.MenuListener.MenuEventKey;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.MenuBar;



public class MainMenuBar extends MenuBar{
	private static final long serialVersionUID = 3041598111655030327L;
	
	private static final ThemeResource reloadIcon = new ThemeResource(
			"../runo/icons/16/reload.png");
	private static final ThemeResource lockIcon = new ThemeResource(
			"../runo/icons/16/lock.png");	
	
	private List<MenuListener> refreshListeners = new ArrayList<MenuListener>();	
	
	public MainMenuBar() {
		
		
		@SuppressWarnings("unused")
		final MenuBar.MenuItem refresh = addItem("Refresh",
				reloadIcon, refreshCommand);

		
		String user = App.get().user();
		@SuppressWarnings("unused")		
		final MenuBar.MenuItem logout = addItem("Logout " + user,
				lockIcon, logoutCommand);
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
