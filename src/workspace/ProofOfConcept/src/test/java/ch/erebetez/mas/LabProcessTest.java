package ch.erebetez.mas;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import junit.framework.Assert;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration(locations = { "/activiti-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LabProcessTest {
	@Resource
	ProcessEngineConfiguration processEngineConfiguration;
	@Resource
	ProcessEngine processEngine;
	@Resource
	RepositoryService repositoryService;
	@Resource
	IdentityService identityService;
	@Resource
	FormService formService;
	@Resource
	TaskService taskService;
	@Resource
	RuntimeService runtimeService;
	@Resource
	HistoryService historyService;
	@Resource
	@Rule
	public ActivitiRule activitiRule;

	private static DatabaseConnection conn;

	@BeforeTransaction
	public void setup() throws Exception {
		System.out.println("setup...");
		IDataSet dataSet = getDataSet();
		DataSource dataSource = processEngineConfiguration.getDataSource();
		conn = new DatabaseConnection(dataSource.getConnection());
		DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
		System.out.println("Database created ...");
	}

	protected IDataSet getDataSet() throws Exception {
		System.out.println("getDataSet...");
		return new FlatXmlDataSetBuilder().build(new File(
				"src/test/resources/act-import.xml"));
	}

	@Test
	@Deployment(resources = "LabProcess.bpmn20.xml")
	public void startLaborWorkflow() {

		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("laborProcess").singleResult();

		List<FormProperty> formList = formService.getStartFormData(def.getId())
				.getFormProperties();
		Assert.assertEquals(1, formList.size());

		// submit registration request
		String workilstid = "WL2012000123";
		Map<String, String> formMap = new HashMap<String, String>();
		formMap.put("worklist", workilstid);
		formService.submitStartFormData(def.getId(), formMap);

		// check if request has been entered correctly
		List<HistoricDetail> historyVars = historyService
				.createHistoricDetailQuery().formProperties().list();
		Assert.assertNotNull(historyVars);
		Assert.assertEquals(1, historyVars.size());
		HistoricFormProperty formProperty = (HistoricFormProperty) historyVars
				.get(0);
		Assert.assertEquals("worklist", formProperty.getPropertyId());
		Assert.assertEquals(workilstid, formProperty.getPropertyValue());

		ProcessInstanceQuery pq = runtimeService.createProcessInstanceQuery();
		ProcessInstance processInstance = pq.singleResult();

		// get the tasks for user kermit
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateUser("kermit").list();
		Assert.assertNotNull(tasks);
		Assert.assertEquals("has exactly one task", 1, tasks.size());
		Assert.assertEquals("found task", "Get the Worklist ID", tasks.get(0)
				.getName());

		// claim the task
		taskService.claim(tasks.get(0).getId(), "kermit");

		// Verify Kermit can now retrieve the task
		tasks = taskService.createTaskQuery().taskAssignee("kermit").list();
		Assert.assertNotNull(tasks);
		Assert.assertEquals("has exactly one task", 1, tasks.size());

		// Complete this task
		taskService.complete(tasks.get(0).getId());

		// get the tasks for user kermit (agian)
		tasks = taskService.createTaskQuery().taskCandidateUser("kermit")
				.list();
		Assert.assertNotNull(tasks);
		Assert.assertEquals("has exactly one task", 1, tasks.size());
		Assert.assertEquals("found task", "Verify the given Worklist", tasks
				.get(0).getName());

		// claim the task
		taskService.claim(tasks.get(0).getId(), "kermit");
		// Complete this task
		taskService.complete(tasks.get(0).getId());

		// verify that the process is actually finished
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();

		Assert.assertNotNull(historicProcessInstance.getEndTime());
	}
}
