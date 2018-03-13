/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author rocket
 */
public class Einsatz {
    
    private Date datum;
    private Time anfahrtszeit;
    private Time abfahrtszeit;
    private Time beginn;
    private Time ende;
    
    //Konstruktoren
    public Einsatz() {
        
    }

    public Einsatz(Date datum, Time anfahrtszeit, Time abfahrtszeit, Time beginn, Time ende) {
        this.datum = datum;
        this.anfahrtszeit = anfahrtszeit;
        this.abfahrtszeit = abfahrtszeit;
        this.beginn = beginn;
        this.ende = ende;
    }
    
}
