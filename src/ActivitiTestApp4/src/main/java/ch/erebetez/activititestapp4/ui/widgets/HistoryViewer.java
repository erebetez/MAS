package ch.erebetez.activititestapp4.ui.widgets;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ch.erebetez.activititestapp4.I18nManager;
import ch.erebetez.activititestapp4.Messages;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)
public class HistoryViewer extends CustomComponent {
	private static final long serialVersionUID = -6787908730426379040L;

	@Autowired
	protected HistoryService historyservice;

	@Autowired
	private I18nManager i18n;

	private VerticalLayout layout = new VerticalLayout();

	private Button executeButton = null;

	private Table dataTable = null;

	public HistoryViewer() {

		layout.setSizeFull();

		// TODO
		Label hello = new Label("TODO. Just shows the finished processes at the moment.");
		layout.addComponent(hello);
		
		layout.addComponent(getExecuteButton());
		layout.addComponent(getDataTable());

		setCompositionRoot(layout);
	}

	public Button getExecuteButton() {
		if (executeButton == null) {
			executeButton = new Button(i18n.get(Messages.BUTTON_SUBMIT));
			executeButton.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 6618266666293209903L;

				@Override
				public void buttonClick(ClickEvent event) {
					populateHistoryTable();
				}
			});
		}
		return executeButton;
	}

	public Table getDataTable() {
		if (dataTable == null) {
			dataTable = new Table();
			dataTable.setImmediate(true);
			dataTable.setSelectable(true);
		}
		return dataTable;
	}

	private void populateHistoryTable() {

		List<HistoricProcessInstance> processList = historyservice
				.createHistoricProcessInstanceQuery().finished()
				.orderByProcessInstanceEndTime().desc().list();


		BeanItemContainer<HistoricProcessInstance> dataSource = new BeanItemContainer<HistoricProcessInstance>(
				HistoricProcessInstance.class, processList);
		
		getDataTable().setContainerDataSource(dataSource);
		
	}

}
