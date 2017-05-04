/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XLOPP2
 */
@Stateless
public class LoginService {
    
    @PersistenceContext
    private EntityManager em;
    
    public Role createRole(Role role){
        this.em.persist(role);
        this.em.flush();
        this.em.refresh(role);
        return role;
    }
    
    public Role getRole(long id){
        return this.em.find(Role.class, id);
    }
    
    public Role getRoleByUser(User user){
        Query query = this.em.createNamedQuery("login.entity.Role.findByUser");
        query.setParameter("username", user.getUsername());
        return (Role) query.getSingleResult();
    }
    
    public User createUser(Role role, User user){
        user.setRole(role);
        this.em.persist(user);
        this.em.flush();
        this.em.refresh(user);
        return user;
    }    

    public User getUser(Long userId) {
        return this.em.find(User.class, userId);
    }
    
    public User getUser(String username, String passwd){
        Query query = this.em.createNamedQuery("login.entity.User.checkUser");
        query.setParameter("username", username);
        query.setParameter("password", passwd);
        return (User) query.getSingleResult();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
