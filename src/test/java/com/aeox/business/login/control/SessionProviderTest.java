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
        User userResult = SessionProvider.getHttpBasicAuthorization(headers);
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
        User userResult = SessionProvider.getHttpBasicAuthorization(headers);
        //Then
        assertNotNull(userResult);
        assertEquals("user", userResult.getUsername());
    }

    /** 
     * Tests for isAuthorized method
     */
    
    
    @SessionSecured(role = "admin")
    public void supportSessionSecuredMethod(){
    }
    
    @Test
    public void shouldIsAuthorizedBySessionOk() throws Exception{
        //Given 
        when(servletRequest.getSession()).thenReturn(httpSession);
        Method resourceMethod = SessionProviderTest.class.getDeclaredMethod("supportSessionSecuredMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
        
        User user = new User();
        Role role = new Role();
        role.setName("admin");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(user);
        when(loginService.getRoleByUser(user)).thenReturn(role);
        
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isAuthorized");
        methodUnderTest.setAccessible(true);
        boolean resTrue = (boolean) methodUnderTest.invoke(underTest);
        
        //Then
        assertTrue(resTrue);
        verify(loginService, times(0)).validateUser(any(), any());
    }
    
    @Test
    public void shouldIsAuthorizedBySessionDeniedBecauseSessionNull() throws Exception{
        //Given 
        when(servletRequest.getSession()).thenReturn(httpSession);
        Method resourceMethod = SessionProviderTest.class.getDeclaredMethod("supportSessionSecuredMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
        
        User user = new User();
        Role role = new Role();
        role.setName("admin");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isAuthorized");
        methodUnderTest.setAccessible(true);
        boolean resFalse = (boolean) methodUnderTest.invoke(underTest);
        
        //Then
        assertFalse(resFalse);
        verify(loginService, times(0)).validateUser(any(), any());
    }
    
    @Test
    public void shouldIsAuthorizedBySessionDeniedRoleDoesntMatch() throws Exception{
        //Given 
        when(servletRequest.getSession()).thenReturn(httpSession);
        Method resourceMethod = SessionProviderTest.class.getDeclaredMethod("supportSessionSecuredMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
        
        User user = new User();
        Role role = new Role();
        role.setName("otherRole");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(user);
        when(loginService.getRoleByUser(user)).thenReturn(role);
        
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isAuthorized");
        methodUnderTest.setAccessible(true);
        boolean resFalse = (boolean) methodUnderTest.invoke(underTest);
        
        //Then
        assertFalse(resFalse);
        verify(loginService, times(0)).validateUser(any(), any());
    }
    
    @Test
    public void shouldIsAuthorizedOkWithHTTPBasic() throws Exception{
        //Given 
        when(servletRequest.getSession()).thenReturn(httpSession);
        Method resourceMethod = SessionProviderTest.class.getDeclaredMethod("supportSessionSecuredMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
        
        User user = new User();
        Role role = new Role();
        role.setName("admin");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        when(loginService.getRoleByUser(any())).thenReturn(role);
        when(loginService.validateUser(any(), any())).thenReturn(user);
        
        List<String> auth = new LinkedList();
        auth.add("Basic "+new String(Base64.base64Encode("user:user".getBytes())));
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(auth);
        
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isAuthorized");
        methodUnderTest.setAccessible(true);
        boolean resTrue = (boolean) methodUnderTest.invoke(underTest);
        
        //Then
        verify(loginService, times(1)).validateUser(any(), any());
        assertTrue(resTrue);
    }
    
    @Test
    public void shouldIsAuthorizedDeniedWithHTTPBasicBecauseRoleDoesntMatch() throws Exception{
        //Given 
        when(servletRequest.getSession()).thenReturn(httpSession);
        Method resourceMethod = SessionProviderTest.class.getDeclaredMethod("supportSessionSecuredMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
        
        User user = new User();
        Role role = new Role();
        role.setName("otherRole");
        user.setRole(role);
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        when(loginService.getRoleByUser(any())).thenReturn(role);
        when(loginService.validateUser(any(), any())).thenReturn(user);
        
        List<String> auth = new LinkedList();
        auth.add("Basic "+new String(Base64.base64Encode("user:user".getBytes())));
        when(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).thenReturn(auth);
        
        //When
        Method methodUnderTest = SessionProvider.class.getDeclaredMethod("isAuthorized");
        methodUnderTest.setAccessible(true);
        boolean resFalse = (boolean) methodUnderTest.invoke(underTest);
        
        //Then
        verify(loginService, times(1)).validateUser(any(), any());
        assertFalse(resFalse);
    }
}
