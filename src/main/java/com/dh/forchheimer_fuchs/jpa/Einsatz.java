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
    @Temporal(TemporalType.DATE)
    private Date datum;
    
    @Column(name = "BEGINN")
    @Temporal(TemporalType.TIME)
    private Time beginn;
    
    @Column(name = "ENDE")
    @Temporal(TemporalType.TIME)
    private Time ende;
    
    @Column(name = "ZEITSPANNE")
    private int zeitspanne;
    
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Einsatz() {
    }
    
    public Einsatz(Date datum, Time beginn, Time ende, int zeitspanne) {
        this.datum = datum;
        this.beginn = beginn;
        this.ende = ende;
        this.zeitspanne = zeitspanne;
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public Date getDatum() {
        return datum;
    }
    
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Time getBeginn() {
        return beginn;
    }
    
    public void setBeginn(Time beginn) {
        this.beginn = beginn;
    }
    
    public Time getEnde() {
        return ende;
    }
    
    public void setEnde(Time ende) {
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
