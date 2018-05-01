/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valerie
 */
@WebServlet(urlPatterns = {"/members/*"})
public class MitgliedServlet extends HttpServlet {
    
    @EJB
    BenutzerBean benutzerBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        
        // Benutzer-Referenz aus den Suche-Parametern heraussuchen
        String mitgliedsnr = request.getParameter("search_mitgliedsnummer");
        String benutzername = request.getParameter("search_username");
        String vorname = request.getParameter("search_vorname");
        String nachname = request.getParameter("search_nachname");
        
        Long mitgliedsnummer = null;
        
        if(mitgliedsnr != null && !mitgliedsnr.trim().isEmpty()){
            mitgliedsnummer = Long.parseLong(mitgliedsnr);
        }
        
        List<Benutzer> users = this.benutzerBean.search(mitgliedsnummer, vorname, nachname, benutzername);

        // Ergebnis-Benutzer aus dem Suchen an das UserEditServlet übergeben (nicht die user_edit.jsp, da in der doGet-Methode des UserEditServlets die jsp aufgerufen wird)
        request.setAttribute("users", users);
        
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/search_user_edit.jsp").forward(request, response);
    }
    
    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // über die URL checken, ob man für ein Event Helfer sucht
        boolean booEvent = false;
        
        request.setAttribute("booEvent", booEvent);
        
        // gewählte Helfer in Event-Objekt speichern
    }
}
