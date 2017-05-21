/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.boundary;

import com.aeox.business.login.entity.Role;
import com.aeox.business.login.entity.User;
import java.util.List;

/**
 *
 * @author pablolm
 */
public interface LoginService {
    public List getUsers();
    public User getUserById(Long id);
    public User validateUser(String username, String passwd);
    public Role getRoleById(Long id);
    public Role getRoleByUser(User user);
    public Role createRole(Role role);
    public User createUser(Role role, User user);
}
