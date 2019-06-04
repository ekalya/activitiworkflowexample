/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.workflowdemo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author exk
 */
@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "userRoles")
    private Collection<UserDetailsImpl> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_authorities",
            joinColumns = @JoinColumn(
                    name = "user_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "id"))
    private Collection<GrantedAuthorityImpl> authorities;

    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserDetailsImpl> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserDetailsImpl> users) {
        this.users = users;
    }

    public Collection<GrantedAuthorityImpl> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthorityImpl> authorities) {
        this.authorities = authorities;
    }

}
