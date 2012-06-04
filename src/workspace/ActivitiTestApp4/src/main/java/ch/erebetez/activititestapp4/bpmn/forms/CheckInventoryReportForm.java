package ch.erebetez.activititestapp4.bpmn.forms;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import ch.erebetez.activititestapp4.dataobjects.InventoryItem;
import ch.erebetez.activititestapp4.ui.util.AbstractUserTaskForm;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class CheckInventoryReportForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355795992791143870L;

	public static final String FORM_KEY = "checkInventoryResultsForm";

	private Table resultTable;
	
	private TextField comments;

	private List<InvetoryTaskDao> invetoryTaskList = new Vector<InvetoryTaskDao>();

	public class InvetoryTaskDao {
		private String name;
		private String ok;
		private String oos;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOk() {
			return ok;
		}

		public void setOk(String ok) {
			this.ok = ok;
		}

		public String getOos() {
			return oos;
		}

		public void setOos(String oos) {
			this.oos = oos;
		}

	}

	@Override
	public String getDisplayName() {
		return "Is the inventory review Ok";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	protected void populateFormInit(String taskId, String executionId) {
		// TODO show some actuall data from the subprocesses.

		List<HistoricProcessInstance> subHist = getHistoryService()
				.createHistoricProcessInstanceQuery()
				.superProcessInstanceId(getTask().getProcessInstanceId())
				.list();

		for (HistoricProcessInstance subInstance : subHist) {
			List<HistoricDetail> detailHist = getHistoryService()
					.createHistoricDetailQuery().formProperties()
					.processInstanceId(subInstance.getId())
					.orderByVariableName().asc()
					.list();

			List<HistoricTaskInstance> taskHist = getHistoryService()
					.createHistoricTaskInstanceQuery()
					.processInstanceId(subInstance.getId()).list();

			// FIXME check list length
			InvetoryTaskDao inventoryTask = new InvetoryTaskDao();
			inventoryTask.setName(taskHist.get(0).getName());

			for (int i = 0; i < detailHist.size(); ++i) {
				HistoricFormProperty detailItem = (HistoricFormProperty) detailHist.get(i);			
				
				if(detailItem.getPropertyId().equals("isItemOk")){
				    inventoryTask.setOk(detailItem.getPropertyValue().toString());
				} 

				if (detailItem.getPropertyId().equals("oosNumber")){
					inventoryTask.setOos(detailItem.getPropertyValue().toString());
				}
				
			}
			
			invetoryTaskList.add(inventoryTask);
		}
		

		BeanItemContainer<InvetoryTaskDao> dataSource = new BeanItemContainer<InvetoryTaskDao>(
				InvetoryTaskDao.class, invetoryTaskList);

		resultTable = new Table();
		resultTable.setContainerDataSource(dataSource);
		addComponent(resultTable);

	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("checkComment")) {
			// comments.setValue(propertyValue);
		}
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("checkComment", (String) comments.getValue());
	}

	@Override
	protected void init() {
		comments = new TextField("Comment");
		addComponent(comments);
	}

}
