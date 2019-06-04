/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import java.util.List;
import java.util.stream.Collectors;


import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author ekalya
 */
@RestController
@RequestMapping("/api/workflowprocessess")
public class ProcessesController {

    @Autowired
    private ProcessRuntime processRuntime;

    @GetMapping("/")
    public String listProcessDefinitions(Model model) {
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        List<ProcessDefinitionDto> pdDtos = processDefinitionPage.getContent().stream().map(pd -> new ProcessDefinitionDto(pd.getId(), pd.getDescription(), pd.getFormKey(), pd.getKey(), pd.getName(), pd.getVersion())).collect(Collectors.toList());
        model.addAttribute("processDefinitions", pdDtos);
        return "listProcessDefinitions";
    }

}
