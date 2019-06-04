/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import java.util.List;
import java.util.stream.Collectors;
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author exk
 */
public class ActivitiUserGroupManager extends ActivitiUserGroupManagerImpl {

    private final UserDetailsService userDetailsService;

    public ActivitiUserGroupManager(UserDetailsService userDetailsService) {
        super(userDetailsService);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public List<String> getUsers() {
        return super.getUsers(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getGroups() {
        return super.getGroups(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getUserGroups(String username) {
        this.userDetailsService.loadUserByUsername(username).getAuthorities().stream()
                .map((GrantedAuthority a) -> a.getAuthority()).forEach(s -> {
            System.out.println("TODO:Remove this print line:Auth:" + s);
        });
        return this.userDetailsService.loadUserByUsername(username).getAuthorities().stream()
                .map((GrantedAuthority a) -> a.getAuthority())
                .collect(Collectors.toList());
    }

    @Override
    public List getUserRoles(String username) {
        this.userDetailsService.loadUserByUsername(username).getAuthorities().stream()
                .map((GrantedAuthority a) -> a.getAuthority()).forEach(r -> {
            System.out.println("TODO:Remove this print line:" + r);
        });
        return this.userDetailsService.loadUserByUsername(username).getAuthorities().stream()
                .map((GrantedAuthority a) -> a.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());
    }

}
