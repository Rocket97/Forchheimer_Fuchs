/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Valerie
 */
@Entity
public class Patient extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PATIENTENNUMMER")
    @NotNull(message = "Die Patientennummer darf nicht leer sein.")
    private long patientennr;
    
    @Column(name = "BEHANDLUNG_VON")
    private Time behandlungVon;
    
    @Column(name = "BEHANDLUNG_BIS")
    private Time behandlungBis;
    
    @ManyToMany
    @NotNull(message = "Der Patient muss mindestens einen Helfer haben.")
    private Benutzer helfer;
    
    @OneToMany
    private Protokoll protokoll;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Patient() { 
        
    }

    public Patient(long patientennr, Time behandlungVon, Time behandlungBis, Benutzer helfer, Protokoll protokoll) {
        this.patientennr = patientennr;
        this.behandlungVon = behandlungVon;
        this.behandlungBis = behandlungBis;
        this.helfer = helfer;
        this.protokoll = protokoll;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public long getPatientennr() {
        return patientennr;
    }

    public void setPatientennr(long patientennr) {
        this.patientennr = patientennr;
    }
    
    public Time getBehandlungVon() {
        return behandlungVon;
    }

    public void setBehandlungVon(Time behandlungVon) {
        this.behandlungVon = behandlungVon;
    }
    
    public Time getBehandlungBis() {
        return behandlungBis;
    }

    public void setBehandlungBis(Time behandlungBis) {
        this.behandlungBis = behandlungBis;
    }
    //</editor-fold>
    
}
