
package com.dh.forchheimer_fuchs.ejb;

import javafx.scene.chart.CategoryAxis;
import javax.ejb.Stateless;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


/**
 *
 * @author Valerie
 * 
 * Die Klasse soll dafür zuständig sein, die Diagramme zu erstellen.
 * Diese sollen dann in den entsprechenden Methoden der ArbeitszeitBean
 */
@Stateless
public class ChartBean {
    
    
    /*==================
    *  TORTENDIAGRAMM
    * ==================*/
    
    public JFreeChart TortendiagrammErstellen (String titel, List<Arbeitszeit> daten){

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        // Daten in das Dataset
        for (){
            pieDataset.setValue("Other", 25);
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
    
    public JFreeChart balkendiagrammErstellen(String titel){
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // dataset.addValue();
        
        
        CategoryAxis xax = new CategoryAxis();
        CategoryAxis yax = new CategoryAxis();
        
        JFreeChart chart = ChartFactory.createBarChart(titel, "Kategorien", "Anzahl der Einsätze", dataset, PlotOrientation.HORIZONTAL, true, true, true);
        return chart;
    }   
}
