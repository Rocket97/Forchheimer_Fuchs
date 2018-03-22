/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import com.dh.forchheimer_fuchs.jpa.StundenKategorie;
import java.util.List;
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
    
    
    public JFreeChart stundenAuswerten(List<Benutzer> helfer, StundenKategorie kategorie, Benutzer benutzer){
        
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
}
