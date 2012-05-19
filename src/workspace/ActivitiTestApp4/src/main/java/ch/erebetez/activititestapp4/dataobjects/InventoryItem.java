package ch.erebetez.activititestapp4.dataobjects;

import java.util.Date;

import org.activiti.engine.task.Task;



public interface InventoryItem {
	
	public final String LOCATION = "location";
	public final String LOT = "lot";

	
	public void setTask(Task task);
	
	public String getId();
	
	
	public String getName();
	
	
	public String getDescription();
	
	
	public int getPriority();
	
	
	public Date getDueDate();
	
	
	public Date getCreateTime();
	

	public String getLocation();
	
	
	public String getLot();
	


	
}
