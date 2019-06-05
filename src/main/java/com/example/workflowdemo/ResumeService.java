/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

/**
 *
 * @author ekalya
 */
import org.springframework.stereotype.Component;

@Component
public class ResumeService {

    public void storeResume() {
        System.out.println("Storing resume ...");
    }

}
