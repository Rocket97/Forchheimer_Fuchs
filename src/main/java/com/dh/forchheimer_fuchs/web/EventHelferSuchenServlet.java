/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.ejb.EventBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import com.dh.forchheimer_fuchs.jpa.Event;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(urlPatterns = {"/searchHelperForEvent/*"})
public class EventHelferSuchenServlet extends HttpServlet {
    
    @EJB
    BenutzerBean benutzerBean;
    
    @EJB
    EventBean eventBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        // Button, um Helfer zu einem Event zuzuordnen hier anzeigen
        request.setAttribute("booEvent", true);
        
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // gefragtes Event, zu dem Helfer zugefügt werden sollen, herausfinden
        Event event = this.getRequestedEvent(request);
        
        // Markierte Helfer IDs auslesen
        String[] helferIds = request.getParameterValues("helfer");

        if (helferIds == null) {
            helferIds = new String[0];
        }

        // Kategorien löschen
        for (String helferId : helferIds) {
            // Zu löschende Helfer ermitteln
            Benutzer helfer;

            try {
                helfer = this.benutzerBean.findById(helferId);
            } catch (NumberFormatException ex) {
                continue;
            }

            if (helfer == null) {
                continue;
            }
            // Helfer hinzufügen
            if (event != null){
                List<Benutzer> eventHelfer = event.getHelfer();
                eventHelfer.add(helfer);
                event.setHelfer(eventHelfer);
                
                // Event updaten
                this.eventBean.update(event);
            } else {
                Logger.getLogger(NormalStundenServlet.class.getName()).log(Level.SEVERE, "Zuerst muss ein Event gespeichert sein, bevor Helfer zugeordnet werden können.");
            }
        }
        
        // zurück zur Seite enter_extra_efforts_edit.jsp von dem entsprechenden Event
        if (event != null){
            response.sendRedirect("/extra_effort/" + event.getEventId());
        } else {
            response.sendRedirect(request.getRequestURI());
        }
    }
    
    private Event getRequestedEvent(HttpServletRequest request){
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll

        // ID aus der URL herausschneiden
        String eventId = request.getPathInfo();

        if (eventId == null) {
            eventId = "";
        }

        eventId = eventId.substring(1);

        if (eventId.endsWith("/")) {
            eventId = eventId.substring(0, eventId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        Event event = null;
        
        try{
            event = this.eventBean.findById(Long.parseLong(eventId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }
       
        return event;
    }
}
