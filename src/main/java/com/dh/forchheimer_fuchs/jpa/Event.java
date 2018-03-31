/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Valerie
 */
@Entity
public class Event extends Einsatz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private long eventId;
    
    @Column(name = "TITEL")
    private String eTitel;
    
    @Column(name = "abteilung")
    private String abteilung;
    
    @OneToMany
    private List<Protokoll> protokoll;
    
    @ManyToMany
    private List<Benutzer> helfer;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Event() {
    }
    
    public Event(Date datum, Time beginn, Time ende, int zeitspanne, long eventId, String eTitel, String abteilung, List<Protokoll> protokoll, List<Benutzer> helfer) {
        super(datum, beginn, ende, zeitspanne);
        this.eventId = eventId;
        this.eTitel = eTitel;
        this.abteilung = abteilung;
        this.protokoll = protokoll;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public long getEventId() {
        return eventId;
    }
    
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
    
    public String getAbteilung() {
        return abteilung;
    }
    
    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }
    
    public String geteTitel() {
        return eTitel;
    }
    
    public void seteTitel(String eTitel) {
        this.eTitel = eTitel;
    }
    
    public List<Protokoll> getProtokoll() {
        return protokoll;
    }
    
    public void setProtokoll(List<Protokoll> protokoll) {
        this.protokoll = protokoll;
    }
    
    public List<Benutzer> getHelfer() {
        return helfer;
    }
    
    public void setHelfer(List<Benutzer> helfer) {
        this.helfer = helfer;
    }
    //</editor-fold>
    
}
