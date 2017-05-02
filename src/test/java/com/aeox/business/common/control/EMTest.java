/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.control;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author XLOPP2
 */
public class EMTest {
    
    private EntityManager em;
    private EntityTransaction tx;
    
    public EMTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tests");
        this.em = emf.createEntityManager();
        this.tx = this.em.getTransaction();
    }
    
    @After
    public void tearDown() {
        this.em.close();
    }

    /**
     * Testing entity manager
     */
    @Test
    public void testEntityManager() {
        tx.begin();
        
        
        tx.commit();
    }
    
}
