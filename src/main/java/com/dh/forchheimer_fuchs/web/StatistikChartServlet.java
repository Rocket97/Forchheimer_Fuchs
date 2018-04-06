/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author rocket
 */
@WebServlet(urlPatterns = {"/statistic/chart"})
public class StatistikChartServlet extends HttpServlet {
    @EJB
    ArbeitszeitBean arbeitszeitBean;
    
    @EJB
    BenutzerBean benutzerBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Parameter auslesen und prüfen
        String benutzername = request.getParameter("benutzername");
        String von = request.getParameter("von");
        String bis = request.getParameter("bis");
        String type = request.getParameter("type");
        
        if (benutzername == null || benutzername.trim().isEmpty()
                || von == null || von.trim().isEmpty()
                || bis == null || bis.trim().isEmpty()
                || type == null || type.trim().isEmpty()) {
            response.setStatus(response.SC_BAD_REQUEST);
            return;
        }
        
        // von/bis als DATE parsen
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date beginn = null, ende = null;
        try {
            beginn = formatter.parse(von);
            ende = formatter.parse(bis);
        } catch (ParseException ex) {
            Logger.getLogger(NormalStundenServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        // Benutzer herausfinden 
        Benutzer benutzer = this.benutzerBean.findById(benutzername);
        
        // Daten einlesen und Schaubild erzeugen
        JFreeChart chart = arbeitszeitBean.stundenAuswertenEinzeln(benutzer, beginn, ende, type);
        
        // TODO: Binärdaten vom Chart zurückgeben
        
    }
}
