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
@MappedSuperclass
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "NACHNAME")
    @NotNull(message = "Der Nachname darf nicht leer sein.")
    private String nachname;
    
    @Column(name = "VORNAME")
    @NotNull(message = "Der Vorname darf nicht leer sein.")
    private String vorname;
    
    @Column(name = "STRASSE")
    @NotNull(message = "Die Straße darf nicht leer sein.")
    private String strasse;
    
    @Column(name = "HAUSNUMMER")
    @NotNull(message = "Die Hausnummer darf nicht leer sein.")
    @Pattern(regexp = "^[0-9]+[a-zA-Z]?$", message = "Die Hausnummer muss eine Zahl (mit eventuellem Buchstaben dahinter) sein.")
    private String hausnr;
    
    @Column(name = "PLZ")
    @Size(min = 5, max = 5, message = "Die PLZ muss 5 Zeichen haben.")
    @NotNull(message = "Die PLZ darf nicht leer sein.")
    @Pattern(regexp = "[0-9]+", message = "Die PLZ muss eine Zahl sein.")
    private String plz;
    
    @Column(name = "ORT")
    @NotNull(message = "Der Ort darf nicht leer sein.")
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