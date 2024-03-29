/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.common.boundary.ErrorMessage;
import com.aeox.business.logger.control.LogInterceptor;
import com.aeox.business.login.control.CORSEnabled;
import com.aeox.business.login.control.SessionProvider;
import com.aeox.business.login.control.SessionSecured;
import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author XLOPP2
 */
@Path("login")
@CORSEnabled(domain = "http://localhost:4200", allowCredentials = true)
@Interceptors(LogInterceptor.class)
public class LoginResource {
    
    @Inject
    private LoginService loginService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request,
            @Context HttpHeaders headers){
        HttpSession session = request.getSession(true);
        User userReceived = SessionProvider.getHttpBasicAuthorization(headers);
        if (userReceived == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(400, "Authorization header is not attached")).build();
        User userFound = loginService.validateUser(userReceived.getUsername(), userReceived.getPassword());
        if(userFound == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(404, "User does not exist")).build();
        session.setAttribute("user", userFound);
        return Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    public Response logout(@Context HttpServletRequest request,
            User user){
        HttpSession session = request.getSession(true);
        session.removeAttribute("user");
        session.invalidate();
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Path("roles")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @SessionSecured(role = "admin")
    public Response createRole(Role role){
        Role roleCreated = loginService.createRole(role);
        return Response.status(Response.Status.CREATED).entity(roleCreated).build();
    }
    
    @POST
    @Path("roles/{role_id}/users")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @SessionSecured(role = "admin")
    public Response createAccount(@PathParam("role_id") Long roleId, User user){
        Role role = loginService.getRoleById(roleId);
        User userCreated = loginService.createUser(role, user);
        return Response.status(Response.Status.CREATED).entity(userCreated).build();
    }
    
    @GET
    @Path("users")
    @Produces(value = MediaType.APPLICATION_JSON)
    @SessionSecured(role = "admin")
    public Response getUsers(){
        
        return Response.status(Response.Status.OK).entity(loginService.getUsers()).build();
    }
    
    @GET
    @Path("users/{user_id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    @SessionSecured(role = "admin")
    public Response getUser(@PathParam("user_id") Long userId){
        User userFound = loginService.getUserById(userId);
        return Response.status(Response.Status.OK).entity(userFound).build();
    }
    
}
