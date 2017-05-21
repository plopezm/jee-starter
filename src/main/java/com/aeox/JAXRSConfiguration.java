package com.aeox;

import com.aeox.business.common.exception.OptimisticLockExceptionMapper;
import com.aeox.business.login.boundary.LoginResource;
import com.aeox.business.login.control.CORSProvider;
import com.aeox.business.login.control.SessionProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.persistence.jpa.rs.exceptions.NoResultExceptionMapper;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@ApplicationPath("api/v1")
public class JAXRSConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        //RESOURCES
        classes.add(LoginResource.class);
        
        //MAPPERS
        classes.add(OptimisticLockExceptionMapper.class);
        classes.add(NoResultExceptionMapper.class);
        
        //PROVIDERS
        classes.add(SessionProvider.class);
        classes.add(CORSProvider.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<>();
        instances.add(new JacksonJaxbJsonProvider());
        return instances;
    }
}
