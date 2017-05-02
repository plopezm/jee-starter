/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.entity;

import com.aeox.business.common.entity.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author XLOPP2
 */
@Entity
@Table(name = "auth_role")
public class Role extends AbstractEntity{
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
    
}
