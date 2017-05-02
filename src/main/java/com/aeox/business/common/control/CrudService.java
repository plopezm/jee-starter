/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.control;

import java.util.List;
import java.util.Map;

/**
 *
 * @author XLOPP2
 * @param <T>
 */
public interface CrudService<T> {
    
    public T create(T t);
    public T find(Class clazz, Object id);
    public T update(T t);
    public void delete(Class clazz, Object id);
    public List findWithJPQL(String jpql, Class type, Map parameters, int offset, int limit);
    
}
