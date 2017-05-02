/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.login.boundary.LoginService;
import com.aeox.business.login.entity.Role;
import javax.persistence.EntityManager;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author XLOPP2
 */
public class LoginServiceTest {
    
    public LoginServiceTest() {
    }
    
    private LoginService toTest;
    private EntityManager em;
    
    @Before
    public void setUp(){
        toTest = new LoginService();
        em = mock(EntityManager.class);
    }

    /**
     * Test of createRole method, of class LoginService.
     */
    @Test
    public void testCreateRole() {
    }
    
    @Test
    public void testFindRole(){
        Role mockedRole = new Role();
        mockedRole.setId(1);
        mockedRole.setName("Test");
        
        when(em.find(any(), anyLong())).thenReturn(mockedRole);
        toTest.setEm(em);
        
        assertEquals(1, toTest.getRole(1).getId());
    }
    
}
