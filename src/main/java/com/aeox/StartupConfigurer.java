/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox;

import com.aeox.business.login.boundary.LoginService;
import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author pablolm
 */
@Singleton
@Startup
public class StartupConfigurer {
    
    @Inject
    private LoginService loginService;
    
    @PostConstruct
    public void setFirstAccount(){
        Role roleAdmin = new Role();
        roleAdmin.setName("admin");
        roleAdmin = loginService.createRole(roleAdmin);
        
        Role roleUser = new Role();
        roleUser.setName("user");
        loginService.createRole(roleUser);
        
        User userAdmin = new User();
        userAdmin.setUsername("admin");
        userAdmin.setPassword("admin");
        userAdmin.setRole(roleAdmin);
        
        loginService.createUser(roleAdmin, userAdmin);
        
        User commonUser = new User();
        commonUser.setUsername("test");
        commonUser.setPassword("test");
        commonUser.setRole(roleAdmin);
        
        loginService.createUser(roleUser, commonUser);

    }
    
}
