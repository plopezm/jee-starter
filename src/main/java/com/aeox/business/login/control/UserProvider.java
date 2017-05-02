/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.control;

import com.aeox.business.common.boundary.ErrorMessage;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author pablolm
 */
@Provider
@SessionSecured
public class UserProvider implements ContainerRequestFilter {
    
    @Context
    private HttpServletRequest servletRequest;
    
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        HttpSession session = servletRequest.getSession();
        
        Response errResponse = Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorMessage(401, "You must to log in first")).build();        

        if(session == null){
            containerRequestContext.abortWith(errResponse);
            return;
        }
        
        if(session.getAttribute("userId") == null)
            containerRequestContext.abortWith(errResponse);
        
    }
}
