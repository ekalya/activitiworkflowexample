package com.example.workflowdemo;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class WorkflowDemoApplication {
    
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(WorkflowDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {

        return new CommandLineRunner() {

            public void run(String... strings) throws Exception {

            }
        };
    }
    
    

    @Bean
    InitializingBean processEngineInitializer() {
        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {
                
                processEngineConfiguration.setUserGroupManager(new ActivitiUserGroupManager(userDetailsService));
                
//                processEngineConfiguration.setUserEntityManager(
//                        new SpringSecurityUserManager(processEngineConfiguration,
//                                new MybatisUserDataManager(processEngineConfiguration), userManager));
//                
//                processEngineConfiguration.setGroupEntityManager(
//                        new SpringSecurityGroupManager(processEngineConfiguration,
//                                new MybatisGroupDataManager(processEngineConfiguration)));
            }
        };
    }

//    @Bean
//    public CommandLineRunner run() throws Exception {
//        return args -> {
//            ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
//                    .setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=1000")
//                    .setJdbcUsername("sa")
//                    .setJdbcPassword("password")
//                    .setJdbcDriver("org.h2.Driver")
//                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//            ProcessEngine processEngine = cfg.buildProcessEngine();
//            String pName = processEngine.getName();
//            String ver = ProcessEngine.VERSION;
//            System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");
//
//            RepositoryService repositoryService = processEngine.getRepositoryService();
//            Deployment deployment = repositoryService.createDeployment()
//                    .addClasspathResource("processes/onboarding.bpmn20.xml").deploy();
//            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                    .deploymentId(deployment.getId()).singleResult();
//            System.out.println(
//                    "Found process definition ["
//                    + processDefinition.getName() + "] with id ["
//                    + processDefinition.getId() + "]");
//        };
//    }
}
