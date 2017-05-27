/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XLOPP2
 */
@Stateless
public class LoginServiceDB implements LoginService{
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Role createRole(Role role){
        this.em.persist(role);
        this.em.flush();
        this.em.refresh(role);
        return role;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Role getRoleById(Long id){
        return this.em.find(Role.class, id);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Role getRoleByUser(User user){
        Query query = this.em.createNamedQuery("login.entity.Role.findByUser");
        query.setParameter("username", user.getUsername());
        return (Role) query.getSingleResult();
    }
    
    @Override
    public User createUser(Role role, User user){
        user.setRole(role);
        this.em.persist(user);
        this.em.flush();
        this.em.refresh(user);
        return user;
    }    

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User getUserById(Long userId) {
        return this.em.find(User.class, userId);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User validateUser(String username, String password){
        Query query = this.em.createNamedQuery("login.entity.User.checkUser");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List getUsers() {
        return this.em.createNamedQuery("login.entity.User.all").getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
