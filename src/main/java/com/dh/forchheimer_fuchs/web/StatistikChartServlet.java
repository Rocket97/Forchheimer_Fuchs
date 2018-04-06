/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import java.io.IOException;
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
        
        // Daten einlesen und Schaubild erzeugen
        JFreeChart chart = arbeitszeitBean.stundenAuswertenEinzeln(benutzer, von, bis, type);
        
        // TODO: von/bis als DATE parsen
        // TODO: Binärdaten vom Chart zurückgeben
        
    }
}
