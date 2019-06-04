/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author exk
 */
public interface GrantedAuthoritiesRepository extends JpaRepository<GrantedAuthorityImpl, Long> {
 public GrantedAuthorityImpl findByAuthority(String username);
}
