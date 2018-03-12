/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
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
    private Long protokollId;
    
    private String art;
    
    private String titel;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;
    
    @ManyToOne
    private Patient patient;
    
    //<editor-fold defaultstate="collapsed" desc="Getter uns Setter">
    public Long getProtokollId() {
        return protokollId;
    }

    public void setProtokollId(Long protokollId) {
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
    //</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (protokollId != null ? protokollId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Protokoll)) {
            return false;
        }
        Protokoll other = (Protokoll) object;
        if ((this.protokollId == null && other.protokollId != null) || (this.protokollId != null && !this.protokollId.equals(other.protokollId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dh.forchheimer_fuchs.jpa.Protokoll[ protokollId=" + protokollId + " ]";
    }
    
}
