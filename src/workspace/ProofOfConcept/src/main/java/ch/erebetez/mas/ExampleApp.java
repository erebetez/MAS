package ch.erebetez.mas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.runtime.ProcessInstance;
import org.h2.java.lang.System;

public class ExampleApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Create Activiti process engine
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration()
				.buildProcessEngine();

		// Get Activiti services
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();

		// Deploy the process definition
		repositoryService.createDeployment()
				.addClasspathResource("LabProcess.bpmn20.xml").deploy();

		// System.out.println("Deplyoed.");

		// Start a process instance
//		ProcessInstance def = runtimeService
//				.startProcessInstanceByKey("laborProcess001");
//
//		// submit request
//		String workilstid = "WL2012000124";
//		Map<String, String> formMap = new HashMap<String, String>();
//		formMap.put("worklist", workilstid);
//		formService.submitStartFormData(def.getId(), formMap);

		// The Spring way (not Working yet)
		//
		// ClassPathXmlApplicationContext applicationContext = new
		// ClassPathXmlApplicationContext(
		// "/activiti-context.xml");
		//
		// RepositoryService repositoryService = (RepositoryService)
		// applicationContext
		// .getBean("repositoryService");
		//
		// String deploymentId = repositoryService
		// .createDeployment()
		// .addClasspathResource(
		// "src/main/resources/LabProcess.bpmn20.xml").deploy()
		// .getId();
		//
		// System.out.println("ID:" + deploymentId);

	}

}
