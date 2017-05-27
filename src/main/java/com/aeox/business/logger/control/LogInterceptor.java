/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.logger.control;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author XLOPP2
 */
public class LogInterceptor {

    private static final Logger LOG = Logger.getLogger(LogInterceptor.class.getName());
    
    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception{
        Instant before = Instant.now();
        Object object = ctx.proceed();
        Instant after = Instant.now();
        LOG.log(Level.INFO, "'['{0}']' time consumed: {1} ms", new Object[]{ctx.getMethod(), Duration.between(before, after).toMillis()});
        return object;
    }
    
}
