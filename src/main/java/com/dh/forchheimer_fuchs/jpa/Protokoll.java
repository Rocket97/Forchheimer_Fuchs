/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Valerie
 */
@Entity
public class Protokoll implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Das Protokoll muss eine ID haben.")
    @Column(name = "PROTOKOLL_ID")
    private long protokollId;
    
    //welche Art von Hilfe (Notfallhilfe, SAN-Dienst,...) 
    @Column(name = "ART")
    private String art;
    
    //Titel von dem Protokoll/Name
    @Column(name = "TITEL")
    private String titel;
    
    @Column(name = "DATUM")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    
    @Column(name = "ORT")
    private String ort;
    
    @OneToMany
    private ArrayList<Patient> patient = new ArrayList<>();
    
    @ManyToOne
    private Event event = new Event();
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Protokoll (){
        
    }

    public Protokoll(long protokollId, String art, String titel, Date datum, String ort, ArrayList<Patient> patient, Event event) {
        this.protokollId = protokollId;
        this.art = art;
        this.titel = titel;
        this.datum = datum;
        this.ort = ort;
        this.patient = patient;
        this.event = event;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter uns Setter">
    public long getProtokollId() {
        return protokollId;
    }

    public void setProtokollId(long protokollId) {
        this.protokollId = protokollId;
    }
    
    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
    
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
    
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
    
    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
    //</editor-fold> 
}
