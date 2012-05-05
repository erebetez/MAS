package com.example.activititestapp3;

import com.vaadin.Application;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class Activititestapp3Application extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Activititestapp3 Application");
		Label label = new Label("Hello Vaadin user 2");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
