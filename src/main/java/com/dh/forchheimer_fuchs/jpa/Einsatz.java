/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author rocket
 */
@MappedSuperclass
public class Einsatz {
    
    //eventuell wenn die Spalten nicht gespeichert werden, versuche @Transient --> https://www.java-forum.org/thema/jpa-und-vererbung.155162/
    
    @Column(name = "BEGINN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginn;
    
    @Column(name = "ENDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ende;
    
    @Column(name = "ZEITSPANNE")
    private int zeitspanne;
    
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Einsatz() {
    }
    
    public Einsatz(Date beginn, Date ende, int zeitspanne) {
        this.beginn = beginn;
        this.ende = ende;
        this.zeitspanne = zeitspanne;
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public Date getBeginn() {
        return beginn;
    }
    
    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }
    
    public Date getEnde() {
        return ende;
    }
    
    public void setEnde(Date ende) {
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
