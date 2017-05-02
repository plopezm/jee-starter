/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeox.business.common.entity;

import com.aeox.business.common.entity.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author XLOPP2
 */
@Entity
public class TestEntity extends AbstractEntity{
    
    private String stringAttr;
    private int numberAttr;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public String getString() {
        return stringAttr;
    }

    public void setString(String string) {
        this.stringAttr = string;
    }

    public int getInteger() {
        return numberAttr;
    }

    public void setInteger(int integer) {
        this.numberAttr = integer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
