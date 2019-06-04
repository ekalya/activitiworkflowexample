/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author exk
 */
public interface UserDetailsRepository extends JpaRepository<UserDetailsImpl, Long> {

    public Optional<UserDetailsImpl> findByUsername(String username);
}
