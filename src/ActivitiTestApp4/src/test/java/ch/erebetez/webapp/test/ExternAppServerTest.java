package ch.erebetez.webapp.test;

import static org.junit.Assert.*;

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
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ContextConfiguration(locations = { "/activiti-context-mem-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ExternAppServerTest {

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
	@Deployment(resources = "ch/erebetez/activititestapp4/bpmn/CalculationDemo.bpmn20.xml")
	public void startLaborWorkflow() {

		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey("calculationDemo").singleResult();
	
	
		Assert.assertEquals("Start a demo calculation application", def.getName());
		
		
		
		
		
	}
	


}
