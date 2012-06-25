package ch.erebetez.activititestapp4.ui;

import java.util.EventListener;

public interface MenuListener extends EventListener {

	  public static enum MenuEventKey{
		  RELOAD,
		  LOGOUT;
	  }
	
	  void menuEvent(MenuEventKey key);
}
