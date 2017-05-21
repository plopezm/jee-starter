/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.entity;

import com.aeox.business.common.entity.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author XLOPP2
 */
@Entity
@Table(name = "auth_user")
@NamedQueries({
    @NamedQuery(name = "login.entity.User.all", query = "SELECT u FROM User u"),
    @NamedQuery(name = "login.entity.User.checkUser", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
})
public class User extends AbstractEntity{
    
    @Column(unique=true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
    
}
