/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Valerie
 */
@Entity
public class Arbeitszeit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long zeitId;
    
    @ManyToOne
    private Benutzer helfer;
    
    //Konstruktoren
    public Arbeitszeit() {
        
    }

    public Arbeitszeit(long zeitId, Benutzer helfer) {
        this.zeitId = zeitId;
        this.helfer = helfer;
    }
    
    // Setter und Getter
    public long getZeitId() {
        return zeitId;
    }

    public void setZeitId(long id) {
        this.zeitId = zeitId;
    }
}
