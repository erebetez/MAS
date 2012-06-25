package ch.erebetez.activititestapp4.ui.widgets;

import java.util.EventListener;

import org.activiti.engine.task.Task;

public interface ShowFormListener extends EventListener{

	  void showFormForTask(Task task);

}
