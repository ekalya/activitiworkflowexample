/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author exk
 */
@RestController
@RequestMapping("/api/vacations")
public class VacationRequestsController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Object> applyforavacation(@RequestBody VacationRequest vacationRequest) {
        //deploy the process definition    
        Map<String, Object> vacationRequestMap = new HashMap<>();
        vacationRequestMap.put("employeeName", vacationRequest.getEmployeeName());
        vacationRequestMap.put("numberOfDays", vacationRequest.getNumberOfDays());
        vacationRequestMap.put("reason", vacationRequest.getVacationMotivation());
        vacationRequestMap.put("approver", vacationRequest.getApprover());
        vacationRequestMap.put("owner", vacationRequest.getOwner());

        Map<String, Object> vacationRequestApplicationMap = new HashMap<>();
        vacationRequestApplicationMap.put("application", vacationRequestMap);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacation-request", vacationRequestMap);
        System.out.println("instance activity Id:" + processInstance.getActivityId());
        System.out.println("instance Id:" + processInstance.getId());

        return new ResponseEntity<>(vacationRequestMap, OK);
    }

    @PostMapping("/reject")
    public ResponseEntity<Task> reject(@RequestBody VacationRequestApproveReject vacationRequestApproveReject, Principal principal) {
        // First, the 'phone interview' should be active
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(vacationRequestApproveReject.getId())
                .taskAssignee(principal.getName())
                .singleResult();
        System.out.println("task name:" + task.getName());
        System.out.println("Assignee:" + task.getAssignee());
        System.out.println("Owner:" + task.getOwner());
        // Completing the phone interview with success should trigger two new tasks
        Map<String, Object> taskVariables = new HashMap<String, Object>();
//        taskVariables.put("vacationApproved", false);
//        taskService.complete(task.getId(), taskVariables);
        System.out.println("task execution id:" + task.getExecutionId());
        System.out.println("task id:" + task.getId());
        taskService.setVariableLocal(task.getId(), "vacationApproved", false);
        taskService.complete(task.getId());
        System.out.println("return:" + task.getId());
        return new ResponseEntity<>(task, OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<Task> approve(@RequestBody VacationRequestApproveReject vacationRequestApproveReject, Principal principal) {
        // First, the 'phone interview' should be active
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(vacationRequestApproveReject.getId())
                .taskAssignee(principal.getName())
                .singleResult();

        System.out.println("Who is loggedIn:" + principal.getName());

        System.out.println("task name:" + task.getName());

        // Completing the phone interview with success should trigger two new tasks
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        //taskVariables.put("vacationApproved", true);
        //taskService.complete(task.getId(), taskVariables);
        taskService.setVariableLocal(task.getId(), "vacationApproved", true);
        taskService.complete(task.getId());
        System.out.println("return:" + task.getId());
        return new ResponseEntity<>(task, OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<Task> modify(@RequestBody VacationRequestApproveReject vacationRequestApproveReject, Principal principal) {
        // First, the 'phone interview' should be active
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(vacationRequestApproveReject.getId())
                .taskAssignee(principal.getName())
                .singleResult();

        System.out.println("Who is loggedIn:" + principal.getName());

        System.out.println("task name:" + task.getName());

        // Completing the phone interview with success should trigger two new tasks
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationModified", true);
//        taskVariables.put("numberOfDays", 10);
//        taskService.complete(task.getId(), taskVariables);
        taskService.setVariable(task.getId(), "numberOfDays", 10);
        taskService.setVariableLocal(task.getId(), "vacationModified", true);
        taskService.complete(task.getId());
        System.out.println("return:" + task.getId());
        return new ResponseEntity<>(task, OK);
    }

    @PostMapping("/modifyNot")
    public ResponseEntity<Task> modifyNot(@RequestBody VacationRequestApproveReject vacationRequestApproveReject, Principal principal) {
        // First, the 'phone interview' should be active
        System.out.println("");
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(vacationRequestApproveReject.getId())
                .taskAssignee(principal.getName())
                .singleResult();
        System.out.println("Who is loggedIn:" + principal.getName());

        System.out.println("task name:" + task.getName());

        // Completing the phone interview with success should trigger two new tasks
        Map<String, Object> taskVariables = new HashMap<String, Object>();
//        taskVariables.put("vacationModified", false);
//        taskService.complete(task.getId(), taskVariables);
        taskService.setVariableLocal(task.getId(), "vacationModified", false);
        taskService.complete(task.getId());
        System.out.println("return:" + task.getId());
        return new ResponseEntity<>(task, OK);
    }

}
