/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.control;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XLOPP2
 */
@Stateless
@Local(CrudService.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CrudServiceImpl<T> implements CrudService<T>{

    @PersistenceContext
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }    
    
    @Override
    public T create(T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    @Override
    public T find(Class clazz, Object id) {
        return (T) this.em.find(clazz, id);
    }

    @Override
    public T update(T t) {
        return (T) this.em.merge(t);
    }

    @Override
    public void delete(Class clazz, Object id) {
        Object ref = this.em.getReference(clazz, id);
        this.em.remove(ref);
    }

    @Override
    public List findWithJPQL(String sql, Class type, Map parameters, int offset, int limit) {
        Query query = this.em.createQuery(sql, type);
        
        if(parameters != null)
            parameters.forEach((k, v) -> {
                query.setParameter((String) k, v);
            });
        
        if(offset != limit)
            query.setFirstResult(offset)
                 .setMaxResults(limit);
        
        return query.getResultList();
    }

}
