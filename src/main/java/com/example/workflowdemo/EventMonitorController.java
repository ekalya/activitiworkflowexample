/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

/**
 *
 * @author exk
 */
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventsmonitor")
public class EventMonitorController {

    private static final Log LOG = LogFactory.getLog(EventMonitorController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private ProcessRuntime processRuntime;

    private Map<Integer, String> severities;

    public EventMonitorController() {
        severities = new TreeMap<>();
        severities.put(1, "URGENT");
        severities.put(2, "HIGH PRIORITY");
        severities.put(3, "MEDIUM PRIORITY");
        severities.put(4, "LOW PRIORITY");
        severities.put(5, "COMMENT ONLY");
    }

    @RequestMapping("/")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ResponseEntity<List<ProcessDefinitionDto>> listProcessDefinitions(Model model) {
        System.out.println("TODO:Remove this print line:get processes");

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        System.out.println("User name:" + userId);

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        List<ProcessDefinitionDto> pdDtos = processDefinitionPage.getContent().stream().map(pd -> new ProcessDefinitionDto(pd.getId(), pd.getDescription(), pd.getFormKey(), pd.getKey(), pd.getName(), pd.getVersion())).collect(Collectors.toList());
        model.addAttribute("processDefinitions", pdDtos);
        return new ResponseEntity<>(pdDtos, HttpStatus.OK);

    }

    @RequestMapping("/start")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ResponseEntity<EventDto> startEntry(Model model, @RequestParam("processDefinitionId") String eventProcessDefinitionId) {
        EventDto eventDto = new EventDto();
        eventDto.setEventProcessDefinitionId(eventProcessDefinitionId);
        eventService.logEvent(eventDto);
        return new ResponseEntity<>(eventDto, OK);
    }

    @RequestMapping("/instances")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ResponseEntity< List<ProcessInstanceDto>> listProcessInstances(Model model) {
        List<ProcessInstance> processInstances = processRuntime.processInstances(Pageable.of(0, 10)).getContent();

        List<ProcessInstanceDto> instanceDtos = processInstances.stream().map(instance -> new ProcessInstanceDto(instance.getId(), instance.getName(), instance.getDescription(), instance.getStartDate(), instance.getInitiator(), instance.getBusinessKey(), instance.getStatus().toString(), instance.getProcessDefinitionId(), instance.getProcessDefinitionKey(), processRuntime.processInstanceMeta(instance.getId()).getActiveActivitiesIds())).collect(Collectors.toList());
        model.addAttribute("instances", instanceDtos);

        return new ResponseEntity<>(instanceDtos, OK);
    }

    @RequestMapping("/myTasks")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ResponseEntity<List<TaskDto>> listUsersTasks(Model model) {
        List<TaskDto> taskDtos = eventService.findUserTasks();

        model.addAttribute("tasks", taskDtos);
        model.addAttribute("whose", "My");

        return new ResponseEntity<>(taskDtos, OK);
    }

    @RequestMapping("/reviewTask")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ResponseEntity<EventDto> reviewTask(Model model, @RequestParam("taskId") String taskId) {
        LOG.debug(String.format("In reviewTask with taskId = '%s'", taskId));
        EventDto eventDto = eventService.findTaskById(taskId);
        model.addAttribute("eventDto", eventDto);

        return new ResponseEntity<>(eventDto, OK);
    }

    @RequestMapping(value = "/completeTask", params = {"save"}, method = RequestMethod.POST)
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ResponseEntity<EventDto>  completeTask(@Valid EventDto eventDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            LOG.error("ERRORS: " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> LOG.error("   " + error.toString()));
            return new ResponseEntity<>(eventDto, OK);
        }
        eventService.completeTask(eventDto);

        return new ResponseEntity<>(eventDto, OK);
    }

    @ModelAttribute("severities")
    public Map<Integer, String> getPriorities() {
        return severities;
    }
}
