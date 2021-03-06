/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
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
    @GeneratedValue(generator = "event_ids")
    @TableGenerator(name = "event_ids", initialValue = 0, allocationSize = 50)
    @Column(name="EVENT_ID")
    private long eventId;
    
    @Column(name = "TITEL")
    private String eTitel;
    
    @Column(name = "abteilung")
    private String abteilung;
    
    @ManyToMany(mappedBy="event")
    private List<Benutzer> helfer;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Event() {
    }
    
    public Event(Date beginn, Date ende, int zeitspanne, String eTitel, String abteilung) {
        super(beginn, ende, zeitspanne);
        this.eTitel = eTitel;
        this.abteilung = abteilung;
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
    
    public List<Benutzer> getHelfer() {
        return helfer;
    }
    
    public void setHelfer(List<Benutzer> helfer) {
        this.helfer = helfer;
    }
    //</editor-fold>
    
}
