/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;

/**
 *
 * @author Valerie
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private long eventId;
    
    @Column(name = "abteilung")
    private String abteilung;
    
    @OneToMany
    private ArrayList<Protokoll> protokoll;
    
    @ManyToMany
    private ArrayList<Benutzer> helfer;
    
    //Konstruktor
    public Event() {
    }

    public Event(long eventId, String abteilung, ArrayList<Protokoll> protokoll, ArrayList<Benutzer> helfer) {
        this.eventId = eventId;
        this.abteilung = abteilung;
        this.protokoll = protokoll;
    }
    
    //Setter und Getter
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
    
}
