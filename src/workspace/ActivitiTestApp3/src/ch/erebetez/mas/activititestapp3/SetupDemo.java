package ch.erebetez.mas.activititestapp3;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable(preConstruction = true)
public class SetupDemo {
	
    @Autowired
    protected static IdentityService identityService;	
	
	
	private static final Logger log = Logger
			.getLogger("Setup");
	
	public static void close() {
		log.info("Destroying process engines");
//		ProcessEngines.destroy();
	}


	public static void init() {
		log.info("Initializing process engines");
//		ProcessEngines.init();
		createGroupsIfNotPresent();
		createAdminUserIfNotPresent();
		deployProcesses();
	}

	private static void createAdminUserIfNotPresent() {
		if (!isAdminUserPresent()) {
			createAdminUser();
		}
	}

	private static void createGroupsIfNotPresent() {
		if (!isGroupPresent("managers")) {
			createGroup("managers", "Managers");
		}
		if (!isGroupPresent("developers")) {
			createGroup("developers", "Developers");
		}
		if (!isGroupPresent("reporters")) {
			createGroup("reporters", "Reporters");
		}
	}

	private static boolean isAdminUserPresent() {
		UserQuery query = identityService.createUserQuery();
		query.userId("admin");
		return query.count() > 0;
	}

	private static void createAdminUser() {
		log.info("Creating an administration user with the username 'admin' and password 'password'");
		User adminUser = identityService.newUser("admin");
		adminUser.setFirstName("Arnold");
		adminUser.setLastName("Administrator");
		adminUser.setPassword("password");
		identityService.saveUser(adminUser);
		assignAdminUserToGroups();
	}

	private static void assignAdminUserToGroups() {
		identityService.createMembership("admin", "managers");
		identityService.createMembership("admin", "developers");
		identityService.createMembership("admin", "reporters");
	}

	private static boolean isGroupPresent(String groupId) {
		GroupQuery query = identityService.createGroupQuery();
		query.groupId(groupId);
		return query.count() > 0;
	}

	private static void createGroup(String groupId, String groupName) {
		log.log(Level.INFO,
				"Creating a group with the id '{1}' and name '{2}'",
				new Object[] { groupId, groupName });
		Group group = identityService.newGroup(groupId);
		group.setName(groupName);
		identityService.saveGroup(group);
	}

	private static void deployProcesses() {
		log.info("Deploying processes");
		RepositoryService repositoryService = ProcessEngines
				.getDefaultProcessEngine().getRepositoryService();
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"ch/erebetez/mas/bpmn/PanExample.bpmn20.xml")
				.deploy();
	}

	
	
}
