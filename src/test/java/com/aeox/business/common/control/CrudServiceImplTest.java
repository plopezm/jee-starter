/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.control;

import com.aeox.business.common.control.CrudServiceImpl;
import com.aeox.business.common.entity.TestEntity;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author XLOPP2
 */
public class CrudServiceImplTest {
    
    private CrudServiceImpl<TestEntity> toTest;
    private EntityTransaction tx;
    
    public CrudServiceImplTest() {
    }
    
    @Before
    public void setUp() {
       toTest = new CrudServiceImpl();
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("tests");
       toTest.setEm(emf.createEntityManager());
       this.tx = this.toTest.getEm().getTransaction();
    }
    
    @After
    public void tearDown() {
        this.toTest.getEm().close();
    }

    private TestEntity generateTestEntity(String string, int integer, Date date){
        TestEntity testEntity = new TestEntity();
        testEntity.setDate(date);
        testEntity.setString(string);
        testEntity.setInteger(integer);
        
        return testEntity;
    }
    
    /**
     * Test of create method, of class CrudServiceImpl.
     */
    @Test
    public void testCreate() {
        tx.begin();
        
        TestEntity testEntity = generateTestEntity("hello", 300, new Date());        
        TestEntity testEntityFound = toTest.create(testEntity);
        
        Assert.assertNotNull(testEntity);
        Assert.assertNotNull(testEntityFound);
        Assert.assertEquals(testEntity.getId(), testEntityFound.getId());
        Assert.assertEquals(testEntity.getString(), testEntityFound.getString());
        Assert.assertEquals(testEntity.getInteger(), testEntityFound.getInteger());
        Assert.assertEquals(testEntity.getDate(), testEntityFound.getDate());
        
        tx.rollback();
    }

    /**
     * Test of find method, of class CrudServiceImpl.
     */
    @Test
    public void testFind() {
        tx.begin();
        
        TestEntity testEntity = generateTestEntity("hello", 300, new Date());
        
        testEntity = toTest.create(testEntity);
        TestEntity testEntityFound = toTest.find(TestEntity.class, testEntity.getId());
        
        Assert.assertNotNull(testEntity);
        Assert.assertNotNull(testEntityFound);
        Assert.assertEquals(testEntity.getId(), testEntityFound.getId());
        Assert.assertEquals(testEntity.getString(), testEntityFound.getString());
        Assert.assertEquals(testEntity.getInteger(), testEntityFound.getInteger());
        Assert.assertEquals(testEntity.getDate(), testEntityFound.getDate());
        
        tx.rollback();
    }

    /**
     * Test of update method, of class CrudServiceImpl.
     */
    @Test
    public void testUpdate() {
        tx.begin();
        
        TestEntity testEntity = generateTestEntity("hello", 300, new Date());        
        TestEntity testEntityCreated = toTest.create(testEntity);
        
        testEntityCreated.setString("world");
        testEntityCreated = toTest.update(testEntityCreated);
        testEntity = toTest.find(TestEntity.class, testEntityCreated.getId());

        Assert.assertNotNull(testEntity);
        Assert.assertNotNull(testEntityCreated);
        Assert.assertEquals(testEntity.getId(), testEntityCreated.getId());
        Assert.assertEquals("world", testEntity.getString());
        Assert.assertEquals(testEntity.getInteger(), testEntityCreated.getInteger());
        Assert.assertEquals(testEntity.getDate(), testEntityCreated.getDate());
        
        tx.rollback();
    }

    /**
     * Test of delete method, of class CrudServiceImpl.
     */
    @Test
    public void testDelete() {
        tx.begin();
        
        TestEntity testEntity = generateTestEntity("hello", 300, new Date());
        
        testEntity = toTest.create(testEntity);
        TestEntity testEntityFound = toTest.find(TestEntity.class, testEntity.getId());
        
        Assert.assertNotNull(testEntity);
        Assert.assertNotNull(testEntityFound);
        Assert.assertEquals(testEntity.getId(), testEntityFound.getId());
        Assert.assertEquals(testEntity.getString(), testEntityFound.getString());
        Assert.assertEquals(testEntity.getInteger(), testEntityFound.getInteger());
        Assert.assertEquals(testEntity.getDate(), testEntityFound.getDate());
        
        toTest.delete(TestEntity.class, testEntityFound.getId());
        testEntityFound = toTest.find(TestEntity.class, testEntity.getId());

        Assert.assertNull(testEntityFound);
        
        tx.rollback();
    }

    /**
     * Test of findWithJPQL method, of class CrudServiceImpl.
     */
    @Test
    public void testFindWithNativeQuery() {
        tx.begin();
        
        for(int i=0;i<5;i++){
            toTest.create(generateTestEntity("hello", 300, new Date()));
        }
        List<TestEntity> entities = toTest.findWithJPQL("SELECT te FROM TestEntity AS te", TestEntity.class, null, 0, 0);
        
        Assert.assertEquals(5, entities.size());
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", entities.get(0).getId());
     
        entities = toTest.findWithJPQL("SELECT te FROM TestEntity AS te WHERE te.id = :id", TestEntity.class, parameters, 0, 0);
        
        Assert.assertEquals(1, entities.size());

        tx.rollback();
    }
    
}
