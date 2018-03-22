/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import javafx.scene.chart.CategoryAxis;
import javax.ejb.Stateless;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.*;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;


/**
 *
 * @author Valerie
 */
@Stateless
public class ArbeitszeitBean extends EntityBean<Arbeitszeit, Long> {

    public ArbeitszeitBean() {
        super(Arbeitszeit.class);
    }
    
    
    public JFreeChart stundenAuswerten(){
        

        /*==================
        *  TORTENDIAGRAMM
        * ==================*/
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("JavaWorld", 75);
        pieDataset.setValue("Other", 25);
        JFreeChart chart = ChartFactory.createPieChart
        ("Pie Chart", // Title
        pieDataset, // Dataset
        true,// legend
        false,// tooltips
        false// URL
        );

        // create and display a frame...
        ChartFrame frame = new ChartFrame("Example", chart);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
        
        
        /*==================
        *   BALKENDIAGRAMM
        * ==================*/
        
        CategoryAxis xax = new CategoryAxis();
        CategoryAxis yax = new CategoryAxis();
        
        JFreeChart chart = new JFreeChart();
        return chart;
    }
}
