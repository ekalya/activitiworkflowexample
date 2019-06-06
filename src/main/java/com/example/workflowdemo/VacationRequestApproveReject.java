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
public class VacationRequestApproveReject implements Serializable {

    private String approvalState;
    private String comment;
    private String Id;

    public VacationRequestApproveReject() {
    }
    
    

    public String getApprovalState() {
        return approvalState;
    }

    public void setApprovalState(String approvalState) {
        this.approvalState = approvalState;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    

}
