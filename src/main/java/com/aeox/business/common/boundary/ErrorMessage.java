/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.boundary;

/**
 *
 * @author XLOPP2
 */
public class ErrorMessage {
    
    private int code;
    private String error;

    public ErrorMessage(int code, String errorMsg) {
        this.code = code;
        this.error = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return error;
    }

    public void setErrorMsg(String errorMsg) {
        this.error = errorMsg;
    }
    
    
    
}
