/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.control;

import com.aeox.business.common.boundary.ErrorMessage;
import com.aeox.business.login.boundary.LoginService;
import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.eclipse.persistence.internal.oxm.conversion.Base64;

/**
 *
 * @author pablolm
 */
@Provider
@SessionSecured
public class SessionProvider implements ContainerRequestFilter {
    
    @Inject
    private LoginService loginService;
    
    @Context
    private HttpServletRequest servletRequest;
    
    @Context 
    private HttpHeaders headers;
    
    @Context
    private ResourceInfo resourceInfo;
    
    private User getAuthorization(HttpHeaders headers) {
        List<String> header = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        
        if(header == null || header.isEmpty())
            return null;
        
        String authorization = header.get(0);
        authorization = authorization.substring("Basic ".length());
        
        final String usernameAndPassword = new String(Base64.base64Decode(authorization.getBytes())); 
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            
        User user = new User();
        user.setUsername(tokenizer.nextToken());
        user.setPassword(tokenizer.nextToken());
        return user;
    }
    
    private boolean isSessionAuthorized(HttpSession session, SessionSecured sessionSecured){
        User user;
        
        if(session == null)
            return false;
        
        if((user = (User) session.getAttribute("user")) == null)
            return false;
        
        if(sessionSecured.role().isEmpty())
            return true;
        
        Role userRole = loginService.getRoleByUser(user);
        
        if(userRole == null)
            return false;
        
        return userRole.getName().compareTo(sessionSecured.role()) == 0;
    }
    
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final Response errResponse = Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorMessage(401, "Unauthorized")).build();  
        HttpSession session = servletRequest.getSession();
        
        Method resourceMethod = resourceInfo.getResourceMethod();
        SessionSecured ss = resourceMethod.getDeclaredAnnotation(SessionSecured.class);
        
        if(ss == null){
            Class<?> resourceClass = resourceInfo.getResourceClass();
            ss = resourceClass.getDeclaredAnnotation(SessionSecured.class);
        }
        
        if(isSessionAuthorized(session, ss))
            return;
        
        User user = getAuthorization(headers);
        if(user == null){
            containerRequestContext.abortWith(errResponse);
            return;
        }
        
        user = loginService.validateUser(user.getUsername(), user.getPassword());
        if(user != null)
            return;
        
        containerRequestContext.abortWith(errResponse);
    }
}
