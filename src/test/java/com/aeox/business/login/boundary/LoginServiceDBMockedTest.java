/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 *
 * @author plomarti
 */
public class LoginServiceDBMockedTest {
    private static final Logger LOG = Logger.getLogger(LoginServiceDBMockedTest.class.getName());
    private static final Role ADMIN_ROLE = new Role();
    private static final Role BASIC_ROLE = new Role();
    private static final User ADMIN_USER = new User();
    private static final User BASIC_USER = new User();
    private static final List USER_LIST = new LinkedList();
    
    @Mock
    private EntityManager em;
    @Mock
    private Query query;
    
    @Spy
    @InjectMocks
    private LoginServiceDB underTest;
    
    public LoginServiceDBMockedTest() {
    }
        
    @BeforeClass
    public static void classSetUp(){
        ADMIN_ROLE.setId(1);
        ADMIN_ROLE.setName("admin");
        BASIC_ROLE.setId(2);
        BASIC_ROLE.setName("basic");
        
        ADMIN_USER.setId(1);
        ADMIN_USER.setUsername("admin");
        ADMIN_USER.setPassword("adminpwd");
        ADMIN_USER.setRole(ADMIN_ROLE);
        BASIC_USER.setId(2);
        BASIC_USER.setUsername("basicusr");
        BASIC_USER.setPassword("basicPWD");
        BASIC_USER.setRole(BASIC_ROLE);
        
        USER_LIST.add(ADMIN_USER);
        USER_LIST.add(BASIC_USER);
    }
    
    @Before
    public void setUp() {
        //em = mock(EntityManager.class);   //SAME AS @Mock
        //query = mock(Query.class);        //SAME AS @Mock
        
        //underTest = new LoginServiceDB(); //We don't need this because ->
        //underTest.setEm(em);              //we are using @InjectMocks 

        MockitoAnnotations.initMocks(this); //Required because @InjectMocks
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createRole method, of class LoginServiceDB.
     */
    @Test
    public void testCreateRole() throws Exception {
        //Given
        doNothing().when(em).persist(any());
        doNothing().when(em).flush();
        doNothing().when(em).refresh(any());
        //When
        Role result = underTest.createRole(ADMIN_ROLE);
        //Then
        assertEquals(ADMIN_ROLE.getName(), result.getName());
    }

    /**
     * Test of getRoleById method, of class LoginServiceDB.
     */
    @Test
    public void testGetRoleById() throws Exception {
        //Given
        when(em.find(Role.class, 1L)).thenReturn(ADMIN_ROLE);
        when(em.find(Role.class, 2L)).thenReturn(BASIC_ROLE);
        //When
        Role resultId1 = underTest.getRoleById(1L);
        Role resultId2 = underTest.getRoleById(2L);
        //Then
        assertEquals(ADMIN_ROLE.getName(), resultId1.getName());
        assertEquals(BASIC_ROLE.getName(), resultId2.getName());
    }

    /**
     * Test of getRoleByUser method, of class LoginServiceDB.
     */
    @Test
    public void testGetRoleByUser() throws Exception {
        //Given
        when(em.createNamedQuery(any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(ADMIN_ROLE);
        //When
        Role expectedAdmin = underTest.getRoleByUser(ADMIN_USER);
        //Then
        assertEquals(ADMIN_ROLE.getName(), expectedAdmin.getName());
    }

    /**
     * Test of createUser method, of class LoginServiceDB.
     */
    @Test
    public void testCreateUser() throws Exception {
       //Given
       doNothing().when(em).persist(any());
       doNothing().when(em).flush();
       doNothing().when(em).refresh(any());
       //When
       User result = underTest.createUser(ADMIN_ROLE, ADMIN_USER);
       //Then
       assertEquals(ADMIN_USER.getUsername(), result.getUsername());
       assertEquals(ADMIN_ROLE.getName(), result.getRole().getName());
    }

    /**
     * Test of getUserById method, of class LoginServiceDB.
     */
    @Test
    public void testGetUserById() throws Exception {
        //Given
        when(em.find(User.class, 1L)).thenReturn(ADMIN_USER);
        when(em.find(User.class, 2L)).thenReturn(BASIC_USER);
        //When
        User resultId1 = underTest.getUserById(1L);
        User resultId2 = underTest.getUserById(2L);
        //Then
        assertEquals(ADMIN_USER.getUsername(), resultId1.getUsername());
        assertEquals(BASIC_USER.getUsername(), resultId2.getUsername());
    }

    /**
     * Test of validateUser method, of class LoginServiceDB.
     */
    @Test
    public void testValidateUser() throws Exception {
        //Given
//        when(em.createNamedQuery(any())).thenReturn(query);
//        when(query.setParameter(anyString(), any())).thenReturn(query);
//        
//        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
//        verify(query).setParameter(anyString(), argument.capture());
        
//        when(query.getSingleResult()).thenAnswer((InvocationOnMock iom) -> {
//            String usernameVal = argument.getValue();
//            
//            if(usernameVal.compareTo(LoginServiceDBTest.ADMIN_USER.getUsername()) == 0)
//                return LoginServiceDBTest.ADMIN_USER;
//            
//            if(usernameVal.compareTo(LoginServiceDBTest.BASIC_USER.getUsername()) == 0)
//                return LoginServiceDBTest.BASIC_USER;
//            
//            throw new LoginUnauthorizedException();
//        });
        //When
//        User result1 = underTest.validateUser(ADMIN_USER.getUsername(), ADMIN_USER.getPassword());
//        User result2 = underTest.validateUser(BASIC_USER.getUsername(), BASIC_USER.getPassword());
//        //Then
//        assertEquals(ADMIN_USER.getUsername(), result1.getUsername());
//        assertEquals(BASIC_USER.getUsername(), result2.getUsername());
        
    }

    /**
     * Test of getUsers method, of class LoginServiceDB.
     */
    @Test
    public void testGetUsers() throws Exception {
        //Given
        when(em.createNamedQuery(any())).thenReturn(query);
        when(query.getResultList()).thenReturn(USER_LIST);
        //When
        List allUsers = underTest.getUsers();
        //Then
        assertEquals(USER_LIST.size(), allUsers.size());
    }

}
