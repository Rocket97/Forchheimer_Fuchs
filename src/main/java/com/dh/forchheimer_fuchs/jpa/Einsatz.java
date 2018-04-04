/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.time.ZonedDateTime;
import java.util.Calendar;
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
    
    @Column(name = "BEGINN")
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime beginn;
    
    @Column(name = "ENDE")
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime ende;
    
    @Column(name = "ZEITSPANNE")
    private int zeitspanne;
    
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Einsatz() {
    }
    
    public Einsatz(ZonedDateTime beginn, ZonedDateTime ende, int zeitspanne) {
        this.beginn = beginn;
        this.ende = ende;
        this.zeitspanne = zeitspanne;
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public ZonedDateTime getBeginn() {
        return beginn;
    }
    
    public void setBeginn(ZonedDateTime beginn) {
        this.beginn = beginn;
    }
    
    public ZonedDateTime getEnde() {
        return ende;
    }
    
    public void setEnde(ZonedDateTime ende) {
        this.ende = ende;
    }
    
     public int getZeitspanne() {
        return zeitspanne;
    }

    public void setZeitspanne(int zeitspanne) {
        this.zeitspanne = zeitspanne;
    }
    
    //</editor-fold>
    
}
