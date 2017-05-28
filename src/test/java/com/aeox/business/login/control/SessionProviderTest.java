/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.control;


import com.aeox.business.login.boundary.LoginService;
import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;
import org.eclipse.persistence.internal.oxm.conversion.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 *
 * @author pablolm
 */
public class SessionProviderTest {
    
    public SessionProviderTest() {
    }
    
    @Mock
    private HttpHeaders headers;
    @Mock
    private HttpSession httpSession;
    @Mock
    private SessionSecured sessionSecured;
    @Mock
    private LoginService loginService;
    @Mock
    private HttpServletRequest servletRequest;
    @Mock
    private ResourceInfo resourceInfo;
    
    @Spy
    @InjectMocks
    private SessionProvider underTest;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); //Required because @InjectMocks
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    //In this method we are going to perform a test for private method
    public void testIsSessionAuthorizedWithoutSession() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        //Given
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isSessionAuthorized", HttpSession.class, SessionSecured.class);
        methodUnderTest.setAccessible(true);
        boolean resFalse = (boolean) methodUnderTest.invoke(underTest, httpSession, sessionSecured);
        //Then
        assertEquals(false, resFalse);
    }
    
    @Test
    //In this method we are going to perform a test for private method
    public void testIsSessionAuthorizedWithSessionWithoutRole() throws Exception{
        //Given
        User user = new User();
        Role role = new Role();
        role.setName("testRole");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(user);
        when(sessionSecured.role()).thenReturn("");
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isSessionAuthorized", HttpSession.class, SessionSecured.class);
        methodUnderTest.setAccessible(true);
        boolean resOk = (boolean) methodUnderTest.invoke(underTest, httpSession, sessionSecured);
        //Then
        assertEquals(true, resOk);
    }
    
    @Test
    //In this method we are going to perform a test for private method
    public void testIsSessionAuthorizedWithSessionWithRole() throws Exception{
        //Given
        User user = new User();
        Role role = new Role();
        role.setName("testRole");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(user);
        when(sessionSecured.role()).thenReturn("testRole");
        when(loginService.getRoleByUser(user)).thenReturn(role);
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isSessionAuthorized", HttpSession.class, SessionSecured.class);
        methodUnderTest.setAccessible(true);
        boolean resOk = (boolean) methodUnderTest.invoke(underTest, httpSession, sessionSecured);
        //Then
        assertEquals(true, resOk);
    }
    
    @Test
    //In this method we are going to perform a test for private method
    public void testIsSessionAuthorizedWithSessionWithRoleNoMatch() throws Exception{
        //Given
        User user = new User();
        Role role = new Role();
        role.setName("testRole");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(user);
        when(sessionSecured.role()).thenReturn("otherRole");
        when(loginService.getRoleByUser(user)).thenReturn(role);
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isSessionAuthorized", HttpSession.class, SessionSecured.class);
        methodUnderTest.setAccessible(true);
        boolean resFalse = (boolean) methodUnderTest.invoke(underTest, httpSession, sessionSecured);
        //Then
        assertEquals(false, resFalse);
    }
    
    /**
     * Test of getBasicAuthorization method, of class SessionProvider.
     */
    @Test
    public void testGetBasicAuthorizationEmpty() {
        //Given
        List<String> auth = new LinkedList();
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(auth);
        //When
        User userResult = SessionProvider.getBasicAuthorization(headers);
        //Then
        assertNull(userResult);
    }
    
    @Test
    public void testGetBasicAuthorization() {
        //Given
        List<String> auth = new LinkedList();
        auth.add("Basic "+new String(Base64.base64Encode("user:user".getBytes())));
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(auth);
        //When
        User userResult = SessionProvider.getBasicAuthorization(headers);
        //Then
        assertNotNull(userResult);
        assertEquals("user", userResult.getUsername());
    }

    /**
     * Test of filter method, of class SessionProvider.
     */
    @Test
    public void testFilterSessionSecurizedOk() throws Exception{
        //Given
//        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
//        when(servletRequest.getSession()).thenReturn(httpSession);
//        when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
//        when(resourceInfo.getResourceClass()).thenReturn(resourceClass);
//        when(resourceMethod.getDeclaredAnnotation(any())).thenReturn(sessionSecured);
//        when(resourceClass.getDeclaredAnnotation(any())).thenReturn(sessionSecured);
//        when(resourceInfo.getResourceClass());
//        
//        User user = new User();
//        Role role = new Role();
//        role.setName("testRole");
//        user.setRole(role);
//        when(httpSession.getAttribute(anyString())).thenReturn(user);
//        when(sessionSecured.role()).thenReturn("testRole");
//        when(loginService.getRoleByUser(user)).thenReturn(role);
//        
//        doNothing().when(containerRequestContext).abortWith(any());
        
        //When
//        underTest.filter(containerRequestContext);
        
        
        //Then
        
    }
    
}
