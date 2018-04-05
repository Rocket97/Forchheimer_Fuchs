/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
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
public class Arbeitszeit extends Einsatz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long zeitId;

    @ManyToOne
    private Benutzer helfer;
    
    @Column(name = "STUNDENKATEGORIE")
    private StundenKategorie kategorie;

    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Arbeitszeit() {
        
    }

    public Arbeitszeit(Benutzer helfer, StundenKategorie kategorie, Date beginn, Date ende, int zeitspanne) {
        super(beginn, ende, zeitspanne);
        this.helfer = helfer;
        this.kategorie = kategorie;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public long getZeitId() {
        return zeitId;
    }
    
    public void setZeitId(long id) {
        this.zeitId = id;
    }
    
    public Benutzer getHelfer() {
        return helfer;
    }
    
    public void setHelfer(Benutzer helfer) {
        this.helfer = helfer;
    }
    
    public StundenKategorie getKategorie() {
        return kategorie;
    }
    
    public void setKategorie(StundenKategorie kategorie) {
        this.kategorie = kategorie;
    }
//</editor-fold>
}
