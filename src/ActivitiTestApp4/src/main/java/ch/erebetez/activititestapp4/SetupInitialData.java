package ch.erebetez.activititestapp4;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class SetupInitialData {

    @Autowired
    protected IdentityService identityService;

	@Autowired
	protected RepositoryService repositoryService;
	
	
	private final Logger log = Logger
			.getLogger("Setup");
	

	public void init() {
		log.info("Initializing basic data");
		createGroupsIfNotPresent();
		createAdminUserIfNotPresent();
		createDummyUserIfNotPresent();
		deployProcesses();
	}

	private void createAdminUserIfNotPresent() {
		if (!isAdminUserPresent()) {
			createAdminUser();
		}
	}

	private void createDummyUserIfNotPresent() {
		if (!isDummyUserPresent()) {
			createDummyUser();
		}
	}	
	
	private void createGroupsIfNotPresent() {
		if (!isGroupPresent("labmanagers")) {
			createGroup("labmanagers", "Labmanagers");
		}
		if (!isGroupPresent("reviewers")) {
			createGroup("reviewers", "Reviewers");
		}
		if (!isGroupPresent("analysts")) {
			createGroup("analysts", "Analysts");
		}
		if (!isGroupPresent("001")) {
			createGroup("001", "001");
		}
		if (!isGroupPresent("002")) {
			createGroup("002", "002");
		}
	}

	private boolean isAdminUserPresent() {
		UserQuery query = identityService.createUserQuery();
		query.userId("admin");
		return query.count() > 0;
	}
	
	private boolean isDummyUserPresent() {
		UserQuery query = identityService.createUserQuery();
		query.userId("dummy");
		return query.count() > 0;
	}

	private void createAdminUser() {
		log.info("Creating an administration user with the username 'admin' and password 'password'");
		User adminUser = identityService.newUser("admin");
		adminUser.setFirstName("Arnold");
		adminUser.setLastName("Administrator");
		adminUser.setPassword("password");
		identityService.saveUser(adminUser);
		assignAdminUserToGroups();
	}

	private void createDummyUser() {
		log.info("Creating an dummyuser user with the username 'dummy' and password 'dummy'");
		User dummyUser = identityService.newUser("dummy");
		dummyUser.setFirstName("Dummy");
		dummyUser.setLastName("User");
		dummyUser.setPassword("dummy");
		identityService.saveUser(dummyUser);
		assignDummyUserToGroups();
	}	
	
	private void assignAdminUserToGroups() {
		identityService.createMembership("admin", "labmanagers");
		identityService.createMembership("admin", "reviewers");
		identityService.createMembership("admin", "analysts");
		identityService.createMembership("admin", "001");
		identityService.createMembership("admin", "002");		
	}
	
	private void assignDummyUserToGroups() {
		identityService.createMembership("dummy", "analysts");
		identityService.createMembership("dummy", "001");		
	}	

	private boolean isGroupPresent(String groupId) {
		GroupQuery query = identityService.createGroupQuery();
		query.groupId(groupId);
		return query.count() > 0;
	}

	private void createGroup(String groupId, String groupName) {
		log.log(Level.INFO,
				"Creating a group with the id '{1}' and name '{2}'",
				new Object[] { groupId, groupName });
		Group group = identityService.newGroup(groupId);
		group.setName(groupName);
		identityService.saveGroup(group);
	}

	private void deployProcesses() {
		log.info("Deploying processes");
		
		repositoryService.createDeployment()		
		.addClasspathResource(
				"ch/erebetez/activititestapp4/bpmn/easy.bpmn20.xml")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource(
				"ch/erebetez/activititestapp4/bpmn/dilution.bpmn20.xml")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource(
				"ch/erebetez/activititestapp4/bpmn/DoInventoryItem.bpmn20.xml")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource(
				"ch/erebetez/activititestapp4/bpmn/StartInventory.bpmn20.xml")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource(
				"ch/erebetez/activititestapp4/bpmn/CalculationDemo.bpmn20.xml")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource(
				"ch/erebetez/activititestapp4/bpmn/StartExternApp.bpmn20.xml")
		.deploy();
	}
}
