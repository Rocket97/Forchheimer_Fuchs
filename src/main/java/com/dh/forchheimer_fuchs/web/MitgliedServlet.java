/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Valerie
 */
@WebServlet(urlPatterns = {"/app/members/"})
public class MitgliedServlet extends HttpServlet {
    
    @EJB
    BenutzerBean benutzerBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Benutzer user = this.benutzerBean.getCurrentUser();
        
        // wenn User Admin, dann weiterleiten zur Benutzersuche, ansonsten wieder zurück auf den Home-Bereich schicken (, wenn ein User sich überhaupt auf diese Seite verirrt hat)
        if (user.getAdmin()){
            request.setCharacterEncoding("utf-8");
        
            // Benutzer-Referenz aus den Suche-Parametern heraussuchen
            Long mitgliedsnr = Long.parseLong(request.getParameter("search_mitgliedsnummer"));
            String benutzername = request.getParameter("search_username");
            String vorname = request.getParameter("search_vorname");
            String nachname = request.getParameter("search_nachname");

            List<Benutzer> users = this.benutzerBean.search(mitgliedsnr, vorname, nachname, benutzername);
            List<String> errors = new ArrayList<>();

            if (users.isEmpty()){
                errors.add("Es wurden keine Suchergebnisse gefunden.");
            }

            if(errors.isEmpty()){

            }
            // Ergebnis-Benutzer aus dem Suchen an das UserEditServlet übergeben (nicht die user_edit.jsp, da in der doGet-Methode des UserEditServlets die jsp aufgerufen wird)
            // Anfrage an die JSP weiterleiten
            request.getRequestDispatcher("/WEB-INF/app/search_user_edit.jsp").forward(request, response);

        } else {
            response.sendRedirect(WebUtils.appUrl(request, "/app/home/"));
        }
    }
}
