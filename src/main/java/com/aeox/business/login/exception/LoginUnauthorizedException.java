/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.login.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author XLOPP2
 */
@ApplicationException(rollback = true)
public class LoginUnauthorizedException extends Exception {

    /**
     * Creates a new instance of <code>LoginUnauthorizedException</code> without
     * detail message.
     */
    public LoginUnauthorizedException() {
    }

    /**
     * Constructs an instance of <code>LoginUnauthorizedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginUnauthorizedException(String msg) {
        super(msg);
    }
}
