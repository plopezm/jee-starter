/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.entity;

import com.aeox.business.common.entity.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author XLOPP2
 */
@Entity
@Table(name = "auth_role")
@NamedQueries({
    @NamedQuery(name = "login.entity.Role.findByUser", query = "SELECT r FROM Role r, User u WHERE u.username = :username AND u.role.id = r.id")
})
public class Role extends AbstractEntity{
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
    
}
