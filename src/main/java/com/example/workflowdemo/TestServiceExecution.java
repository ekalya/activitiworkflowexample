/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author exk
 */
public class TestServiceExecution implements JavaDelegate {

    public void execute(DelegateExecution execution) {
//    String configParam01 = (String) execution.getVariable(configParam01);
//    // ...
//
//    RestReponse restResponse = executeRestCall();
//    execution.setTransientVariable("response", restResponse.getBody());
//    execution.setTransientVariable("status", restResponse.getStatus());
        System.out.println("test execution.....current activiti id:" + execution.getCurrentActivityId());
        System.out.println("test execution.....event name:" + execution.getEventName());
        System.out.println("test execution.....execution id:" + execution.getId());

        System.out.println("variable employeeName:" + execution.getVariable("employeeName"));
        System.out.println("variable numberOfDays:" + execution.getVariable("numberOfDays"));
        System.out.println("variable vacationMotivation:" + execution.getVariable("vacationMotivation"));
        System.out.println("variable approver:" + execution.getVariable("approver"));
        System.out.println("variable owner:" + execution.getVariable("owner"));

//        "employeeName": "elisha",
//        "numberOfDays": "10",
//        "vacationMotivation": "Annual vacation",
//        "approver": "peris",
//        "owner": "elisha"
    }
}
