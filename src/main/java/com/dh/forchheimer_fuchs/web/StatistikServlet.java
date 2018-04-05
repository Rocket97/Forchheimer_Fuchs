/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Valerie
 */
@WebServlet(urlPatterns = {"/statistic/"})
public class StatistikServlet extends HttpServlet {
    
    @EJB
    ArbeitszeitBean arbeitszeitBean;
    
    @EJB
    BenutzerBean benutzerBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/statistik.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("statistic_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
        
        // Eingaben des Benutzers auslesen
        Long mitgliedsnr = Long.parseLong(request.getParameter("statistic_mitgliedsnummer"));
        String benutzername = request.getParameter("statistic_username");
        String vorname = request.getParameter("statistic_vorname");
        String nachname = request.getParameter("statistic_nachname");
        String datumVon = request.getParameter("statistic_vonDatum");
        String datumBis = request.getParameter("statistic_bisDatum");
        
        //List<Benutzer> users = this.benutzerBean.search(mitgliedsnr, vorname, nachname, benutzername);
        
        String titel = "Arbeitszeit von " + datumVon + " bis " + datumBis;
        OutputStream out = response.getOutputStream(); /* Get the output stream from the response object */
        response.setContentType("image/png"); /* Set the HTTP Response Type */
//        JFreeChart chart = this.arbeitszeitBean.stundenAuswertenEinzeln(benutzer); // Create chart 
//        ChartUtilities.writeChartAsPNG(out, chart, 400, 300);/* Write the data to the output stream */
    }
}
