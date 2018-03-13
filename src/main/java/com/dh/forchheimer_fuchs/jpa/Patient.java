/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
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
    
    @Column(name = "GESCHLECHT")
    private boolean geschlecht;
    
    @Column(name = "BEHANDLUNG_VON")
    private Time behandlungVon;
    
    @Column(name = "BEHANDLUNG_BIS")
    private Time behandlungBis;
    
    @Column(name = "BESCHREIBUNG")
    private String beschreibung;
    
    @ManyToMany
    @NotNull(message = "Der Patient muss mindestens einen Helfer haben.")
    private ArrayList<Benutzer> helfer = new ArrayList<>();
    
    @ManyToOne
    private Protokoll protokoll = new Protokoll();
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Patient() { 
        
    }

    public Patient(long patientennr, boolean geschlecht, Time behandlungVon, Time behandlungBis, String beschreibung, ArrayList<Benutzer> helfer, Protokoll protokoll) {
        this.patientennr = patientennr;
        this.geschlecht = geschlecht;
        this.behandlungVon = behandlungVon;
        this.behandlungBis = behandlungBis;
        this.beschreibung = beschreibung;
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
    
     public boolean getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(boolean geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    //</editor-fold>

   
    
}
