/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.sql.Time;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author rocket
 */
@MappedSuperclass
public class Einsatz {
    
    //eventuell wenn die Spalten nicht gespeichert werden, versuche @Transient --> https://www.java-forum.org/thema/jpa-und-vererbung.155162/
    
    @Column(name = "DATUM")
    @Past
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    
    @Column(name = "ANFAHRTSZEIT")
    private Time anfahrtszeit;
    
    @Column(name = "ABFAHRTSZEIT")
    private Time abfahrtszeit;
    
    @Column(name = "BEGINN")
    private Time beginn;
    
    @Column(name = "ENDE")
    private Time ende;
    
    //Konstruktoren
    public Einsatz() {
        
    }

    public Einsatz(Date datum, Time anfahrtszeit, Time abfahrtszeit, Time beginn, Time ende) {
        this.datum = datum;
        this.anfahrtszeit = anfahrtszeit;
        this.abfahrtszeit = abfahrtszeit;
        this.beginn = beginn;
        this.ende = ende;
    }
    
}
