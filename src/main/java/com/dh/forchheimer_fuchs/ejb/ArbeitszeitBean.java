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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;


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
    public ApplicationFrame stundenAuswertenAlle(List<Benutzer> helfer, StundenKategorie kategorie){
        
        // eine Kategorie, alle Personen
        
        // Array für die Zeitspannen, die im Diagramm im Verhältnis dargestellt werden sollen, erzeugen
        int[] helferminuten = new int[helfer.size()];
        int endzahlJeKategorie = 0;
        
        // Zeitspannen je Arbeitszeit und Kategorie auslesen und addieren
        for (Benutzer helferlein : helfer){
            int i = 0;
            
            // von einem Benutzer alle Zeitspannen der gegebenen Kategorie raussuchen
            List<Integer> zeitspannen = em.createQuery("SELECT zeitspanne FROM arbeitszeit WHERE a.mitgliedsnr = :mnr AND a.kategorie = :kategorie")
                .setParameter("mnr", helferlein.getMitgliedsnr())
                .setParameter("kategorie", kategorie)
                .getResultList();
            
            // Zeitspannen des einen Benutzers addieren
            for(int zsp : zeitspannen){
                endzahlJeKategorie = endzahlJeKategorie + zsp;
            }
            
            // die Endsumme abspeichern ( ein Platz je Benutzer)
            helferminuten[i] = endzahlJeKategorie;
            
            // wieder auf Null setzen, um für die nächste Kategorie wieder von vorne die Zeitspannen addieren zu können
            endzahlJeKategorie = 0;
            
            // Zähler hochzählen für den nächsten Benutzer in der Liste
            i++;
        }
        
        // Diagramm erstellen mit Hilfe von ChartBean
        JFreeChart pieChart = tortendiagrammErstellen(kategorie.toString(), helferminuten);
        JFreeChart barChart = tortendiagrammErstellen(kategorie.toString(), helferminuten);
        
        return setzeFrameFuerDiagramme(pieChart, barChart);
    }
    
    public ApplicationFrame stundenAuswertenEinzeln(Benutzer benutzer){
        
        // eine Person, alle Kategorien
        
//      Kategorien in dem Array sind wie folgt zugeordnet:
//      ausWeiterbildung [0], hausinstandhaltung [1], verwaltungsarbeit [2], materialpflege [3], fahrzeugwartung [4], jrkVerwaltungsarbeit [5], notfalldarstellung [6],
//      schulsanitätsdienst [7], bereitschaftsabend [8], jahreshauptversammlung [9], verwaltungssitzung [10], kameradschaftspflege[11], jugendbereitschaftsabend [12]

        // Array für die Zeitspannen, die im Diagramm im Verhältnis dargestellt werden sollen, erzeugen
        int[] kategorie = new int[13];
        int endzahlJeKategorie = 0;
        
        // Zeitspannen je Arbeitszeit und Kategorie auslesen und addieren
        for (int i=0; i<StundenKategorie.values().length; i++){
            List<Integer> zeitspannen = em.createQuery("SELECT zeitspanne FROM arbeitszeit WHERE a.mitgliedsnr = :mnr AND a.kategorie = :kategorie")
                .setParameter("mnr", benutzer.getMitgliedsnr())
                .setParameter("kategorie", StundenKategorie.values()[i])
                .getResultList();
            
            // Zeitspannen einer Kategorie addieren
            for(int zsp : zeitspannen){
                endzahlJeKategorie = endzahlJeKategorie + zsp;
            }
            kategorie[i] = endzahlJeKategorie;
            
            // wieder auf Null setzen, um für die nächste Kategorie wieder von vorne die Zeitspannen addieren zu können
            endzahlJeKategorie = 0;
        }
        
        // Diagramm erstellen mit Hilfe von ChartBean
        JFreeChart pieChart = tortendiagrammErstellen(benutzer.getBenutzername(), kategorie);
        JFreeChart barChart = tortendiagrammErstellen(benutzer.getBenutzername(), kategorie);
        
        return setzeFrameFuerDiagramme(pieChart, barChart);
    }
    
    /*==================
    *  TORTENDIAGRAMM
    * ==================*/
    
    public JFreeChart tortendiagrammErstellen (String titel, int[] kategorie){
        
        // Datenset erzeugen
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        
        // Daten in das Dataset laden 
        for (int i = 0; i < kategorie.length; i++){
            pieDataset.setValue(StundenKategorie.values()[i], kategorie[i]);
        }
        
        // ein Tortendiagramm mit den Daten aus dem Datenset erzeugen
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
            dataset.addValue(kategorie[i], "Arbeitszeit", StundenKategorie.values()[i]);
        }
        
        JFreeChart chart = ChartFactory.createBarChart
            (titel, // Titel
            "Kategorien",// x-Achsen-Beschriftung
            "Anzahl der Minuten", //y-Achsen-Beschriftung
            dataset, // Dataset
            PlotOrientation.VERTICAL, // Balken-Ausrichtung
            true, // Legende anzeigen?
            true, // Tooltips?
            false // URLs
        );
        return chart;
    }   
    
//    =======================================
//    Nur, wenn die JSP den Chart nicht nimmt
//    =======================================
    
    public ApplicationFrame setzeFrameFuerDiagramme(JFreeChart pieChart, JFreeChart barChart){
        ApplicationFrame frame = new ApplicationFrame("Diagramme");

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        ChartPanel barChartPanel = new ChartPanel(barChart);
        frame.setContentPane(pieChartPanel);
        frame.setContentPane(barChartPanel);
        frame.pack();
        frame.setVisible(true);
        
        return frame;
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
