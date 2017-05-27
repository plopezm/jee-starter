/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pablolm
 */
public class LoginServiceDBTest {
    
    private EntityTransaction tx;
    private EntityManager em;
    private LoginServiceDB underTest;
    
    @Before
    public void setUp() {
        underTest = new LoginServiceDB();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tests");
        em = emf.createEntityManager();
        underTest.setEm(em);
        tx = em.getTransaction();
    }
    
    @After
    public void tearDown() {
        em.close();
    }

    /**
     * Test of createRole method, of class LoginServiceDB.
     */
    @Test
    public void testCreateRole() throws Exception {
        Role role = new Role();
        role.setName("admin");
        
        tx.begin();
        
        Role created = underTest.createRole(role);
        
        assertEquals(created, em.find(Role.class, created.getId()));
        
        tx.rollback();
    }

    /**
     * Test of getRoleById method, of class LoginServiceDB.
     */
    @Test
    public void testGetRoleById() throws Exception {
        Role role = new Role();
        role.setName("admin");
        
        tx.begin();
        
        Role created = underTest.createRole(role);
        
        assertEquals(created, em.find(Role.class, created.getId()));
        
        assertNull(em.find(Role.class, 100L));
        
        tx.rollback();
    }

    /**
     * Test of getRoleByUser method, of class LoginServiceDB.
     */
    @Test
    public void testGetRoleByUser() throws Exception {
    }

    /**
     * Test of createUser method, of class LoginServiceDB.
     */
    @Test
    public void testCreateUser() throws Exception {
        Role role = new Role();
        role.setName("admin");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        
        tx.begin();
        
        underTest.createRole(role);
        User created = underTest.createUser(role, user);
        
        assertEquals(role, created.getRole());
        assertEquals(user.getUsername(), created.getUsername());
        
        tx.rollback();
    }

    /**
     * Test of getUserById method, of class LoginServiceDB.
     */
    @Test
    public void testGetUserById() throws Exception {
        Role role = new Role();
        role.setName("admin");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        
        tx.begin();
        
        underTest.createRole(role);
        User created = underTest.createUser(role, user);
        User userFound = underTest.getUserById(created.getId());
        
        assertNotNull(userFound);
        assertEquals(created.getUsername(), userFound.getUsername());
        
        tx.rollback();
    }

    /**
     * Test of validateUser method, of class LoginServiceDB.
     */
    @Test
    public void testValidateUser() throws Exception {
        Role role = new Role();
        role.setName("admin");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        
        tx.begin();
        
        underTest.createRole(role);
        User created = underTest.createUser(role, user);
        User userFound = underTest.validateUser(created.getUsername(), created.getPassword());
        
        assertNotNull(userFound);
        assertEquals(created.getUsername(), userFound.getUsername());
        
        tx.rollback();
    }

    /**
     * Test of getUsers method, of class LoginServiceDB.
     */
    @Test
    public void testGetUsers() throws Exception {
        Role role = new Role();
        role.setName("admin");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        
        tx.begin();
        
        underTest.createRole(role);
        User created = underTest.createUser(role, user);
        List usersFound = underTest.getUsers();
        
        assertNotNull(usersFound);
        assertEquals(1, usersFound.size());
        
        tx.rollback();
    }
    
}
