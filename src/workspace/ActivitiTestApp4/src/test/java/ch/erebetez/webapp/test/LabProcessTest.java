package ch.erebetez.webapp.test;


import javax.annotation.Resource;

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
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.ui.Label;

@Transactional
@ContextConfiguration(locations = { "/activiti-context-mem-test.xml" })
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

	@Test
	@Deployment(resources = "ch/erebetez/activititestapp4/bpmn/easy.bpmn20.xml")
	public void startLaborWorkflow() {

		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("easyProcess").singleResult();
	
	
		Assert.assertEquals("Dummy test Process", def.getName());
	}
	
	
}
