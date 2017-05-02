/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.exception;

import com.aeox.business.common.boundary.ErrorMessage;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author XLOPP2
 */
@Provider
public class OptimisticLockExceptionMapper implements ExceptionMapper<OptimisticLockException>{
    @Override
    public Response toResponse(OptimisticLockException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(409, "The object is not up to date"))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
