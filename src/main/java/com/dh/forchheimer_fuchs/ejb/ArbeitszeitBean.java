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
import javafx.scene.chart.CategoryAxis;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


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
    
    public JFreeChart stundenAuswertenEinzeln(Benutzer benutzer){
        
//        ausWeiterbildung [0], hausinstandhaltung [1], verwaltungsarbeit [2], materialpflege [3], fahrzeugwartung [4], jrkVerwaltungsarbeit [5], notfalldarstellung [6],
//        schulsanitätsdienst [7], bereitschaftsabend [8], jahreshauptversammlung [9], verwaltungssitzung [10], kameradschaftspflege[11], jugendbereitschaftsabend [12]
        int[] kategorie = new int[13];
        int endzahlJeKategorie = 0;
        // Zeitspannen je Arbeitszeit und Kategorie auslesen und addieren
        for (int i=0; i<StundenKategorie.values().length; i++){
            List<Integer> zeitspannen = em.createQuery("SELECT zeitspanne FROM arbeitszeit WHERE mitgliedsnr = :mnr AND kategorie = :kategorie")
                .setParameter("mnr", benutzer.getMitgliedsnr())
                .setParameter("kategorie", StundenKategorie.values()[i])
                .getResultList();
            
            for(int zsp : zeitspannen){
                endzahlJeKategorie = endzahlJeKategorie + zsp;
            }
            kategorie[i] = endzahlJeKategorie;
            // wieder auf Null setzen, um für die nächste Kategorie wieder von vorne die Zeitspannen addieren zu können
            endzahlJeKategorie = 0;
        }
        
        // Diagramm erstellen mit Hilfe von ChartBean
        JFreeChart chart = tortendiagrammErstellen(benutzer.getBenutzername(), kategorie);
        
        return chart;
    }
    
    /*==================
    *  TORTENDIAGRAMM
    * ==================*/
    
    public static JFreeChart tortendiagrammErstellen (String titel, int[] kategorie){

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        // Daten in das Dataset
        for (int i = 0; i < kategorie.length; i++){
            pieDataset.setValue(StundenKategorie.values()[i], kategorie[i]);
        }
        
        
        JFreeChart chart = ChartFactory.createPieChart
            (titel, // Title
            pieDataset, // Dataset
            true,// legend
            false,// tooltips
            false// URL
        );
        
        return chart;
        
    }
    
    
    /*==================
    *   BALKENDIAGRAMM
    * ==================*/
    
    public JFreeChart balkendiagrammErstellen(String titel, int[] kategorie){
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // dataset.addValue();
        for (int i = 0; i < kategorie.length; i++){
            dataset.addValue(StundenKategorie.values()[i], kategorie[i]);
        }
        
        
        CategoryAxis xax = new CategoryAxis();
        CategoryAxis yax = new CategoryAxis();
        
        JFreeChart chart = ChartFactory.createBarChart(titel, "Kategorien", "Anzahl der Einsätze", dataset, PlotOrientation.HORIZONTAL, true, true, true);
        return chart;
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
