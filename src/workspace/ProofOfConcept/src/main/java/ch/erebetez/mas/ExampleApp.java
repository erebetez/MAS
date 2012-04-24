package ch.erebetez.mas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.activiti.engine.RepositoryService;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class ExampleApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		 The Spring way
		
		 ClassPathXmlApplicationContext applicationContext = new
		 ClassPathXmlApplicationContext(
		 "/activiti-context.xml");
		
		 RepositoryService repositoryService = (RepositoryService)
		 applicationContext
		 .getBean("repositoryService");
		
		 String deploymentId = repositoryService
		 .createDeployment()
		 .addClasspathResource(
		 "LabProcess.bpmn20.xml").deploy()
		 .getId();
		
		 System.out.println("ID:" + deploymentId);

	}

}
