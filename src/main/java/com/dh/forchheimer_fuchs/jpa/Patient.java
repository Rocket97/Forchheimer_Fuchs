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
    private Long patientennr;
    
    @Column(name = "BEHANDLUNG_VON")
    private Time behandlungVon;
    
    @Column(name = "BEHANDLUNG_BIS")
    private Time behandlungBis;
    
    
    // Das geht nicht auf, denn es ist nicht sinnvoll bei Benutzer Patienten reinzuspeichern!!!!
    @OneToMany
    @NotNull(message = "Ein Patient muss einen Helfer haben.")
    private Benutzer helfer;
    
    @Column(name = "RETTUNGSDIENST")
    @NotNull(message = "Die Checkbox Rettungsdienst darf nicht leer sein.")
    private Boolean rettungsdienst;
    
    @OneToMany
    private Protokoll protokoll;
    
    //<editor-fold defaultstate="collapsed" desc="Getter uns Setter">
    public Long getPatientennr() {
        return patientennr;
    }

    public void setPatientennr(Long patientennr) {
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
    
    public Boolean getRettungsdienst() {
        return rettungsdienst;
    }

    public void setRettungsdienst(Boolean rettungsdienst) {
        this.rettungsdienst = rettungsdienst;
    }
    //</editor-fold>
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientennr != null ? patientennr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.patientennr == null && other.patientennr != null) || (this.patientennr != null && !this.patientennr.equals(other.patientennr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dh.forchheimer_fuchs.jpa.Patient[ patientennr=" + patientennr + " ]";
    }
    
}
