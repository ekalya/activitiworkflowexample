/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import java.io.Serializable;

/**
 *
 * @author exk
 */
public class VacationRequest implements Serializable {

    private String employeeName;
    private Long numberOfDays;
    private String vacationMotivation;
    private String approver;
    private String updateKey;
    private String owner;

    public VacationRequest() {
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getVacationMotivation() {
        return vacationMotivation;
    }

    public void setVacationMotivation(String vacationMotivation) {
        this.vacationMotivation = vacationMotivation;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getUpdateKey() {
        return updateKey;
    }

    public void setUpdateKey(String updateKey) {
        this.updateKey = updateKey;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
