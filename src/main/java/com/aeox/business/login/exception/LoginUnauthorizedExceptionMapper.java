/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.exception;

import com.aeox.business.common.boundary.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author XLOPP2
 */
@Provider
public class LoginUnauthorizedExceptionMapper implements ExceptionMapper<LoginUnauthorizedException>{

    @Override
    public Response toResponse(LoginUnauthorizedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage(401, "User unauthorized"))
                .type(MediaType.APPLICATION_JSON).build();
    }
    
}
