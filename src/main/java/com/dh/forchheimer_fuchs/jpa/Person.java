/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Valerie
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "NACHNAME")
    private String nachname;
    
    @Column(name = "VORNAME")
    private String vorname;
    
    @Column(name = "STRASSE")
    private String strasse;
    
    @Column(name = "HAUSNUMMER")
    private String hausnr;
    
    @Column(name = "PLZ")
    @Size(min = 5, message = "Die PLZ muss 5 Zeichen haben.")
    private String plz;
    
    @Column(name = "ORT")
    private String ort;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Person() {
        
    }
    
    public Person(String nachname, String vorname, String strasse, String hausnr, String plz, String ort) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    
    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }
    
    public String getHausnr() {
        return hausnr;
    }

    public void setHausnr(String hausnr) {
        this.hausnr = hausnr;
    }
    
    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }
    
    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
    //</editor-fold>
    
}