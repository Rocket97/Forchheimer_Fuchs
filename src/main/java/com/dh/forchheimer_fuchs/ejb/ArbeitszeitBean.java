/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import com.dh.forchheimer_fuchs.jpa.StundenKategorie;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import org.jfree.chart.JFreeChart;


/**
 *
 * @author Valerie
 */
@Stateless
public class ArbeitszeitBean extends EntityBean<Arbeitszeit, Long> {

    public ArbeitszeitBean() {
        super(Arbeitszeit.class);
    }
    
    @RolesAllowed("ff_admin")
    public JFreeChart stundenAuswertenAlle(List<Benutzer> helfer, StundenKategorie kategorie, Benutzer benutzer){
        
        // der Admin darf mehr Daten sehen
        String select = "";
        if (benutzer.getAdmin()){
            select = "SELECT COUNT (zeitID) FROM arbeitszeit WHERE";
            for (Benutzer nutzer : helfer){
                select = select + " mitgliedsnr = " + nutzer.getMitgliedsnr() + ", ";
            }
            select = select + " AND kategorie = " + kategorie;
        } else {
            
        }
        em.createQuery(select);
        return ;
    }
    
    public JFreeChart stundenAuswertenEinzeln(Benutzer helfer, Benutzer benutzer){
        
        // der Admin darf mehr Daten sehen
        if (benutzer.getAdmin()){
            int zahl = em.createQuery("SELECT zeitspanne FROM arbeitszeit WHERE mitgliedsnr = :mnr AND kategorie = :kategorie");
        }
        return ;
    }
        
    
    public int berechneZeitspanne(Calendar from, Calendar to) {
        Date fromDate = from.getTime();
        Date toDate = to.getTime();
        long difference = fromDate.getTime() - toDate.getTime();
        return (int) (difference / 60000);
    }
    
    public List<Arbeitszeit> findAllSorted(Benutzer benutzer){
        return em.createQuery("SELECT a FROM arbeitszeit a WHERE a.helfer = :benutzer").setParameter("benutzer", benutzer).getResultList();
    } 
}
