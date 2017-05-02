/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.exception;

import com.aeox.business.common.boundary.ErrorMessage;
import javax.persistence.NoResultException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author pablolm
 */
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException>{

    @Override
    public Response toResponse(NoResultException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(404, "It Does not exist"))
                .type(MediaType.APPLICATION_JSON).build();
    }
    
}
