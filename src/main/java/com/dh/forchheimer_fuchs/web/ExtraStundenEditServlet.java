/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import com.dh.forchheimer_fuchs.ejb.EventBean;
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import static com.dh.forchheimer_fuchs.jpa.Benutzer_.arbeitszeit;
import com.dh.forchheimer_fuchs.jpa.Event;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
@WebServlet(urlPatterns = {"/extra_effort/*"})
public class ExtraStundenEditServlet extends HttpServlet {
    
    @EJB
    EventBean eventBean;
    
    @EJB
    ArbeitszeitBean arbeitszeitBean;
    
    @EJB
    ValidationBean validationBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Zu bearbeitende Arbeitszeit einlesen
        HttpSession session = request.getSession();
        
        Event event = this.getRequestedEvent(request);
        
        // Events vorbelegen, wenn eines übergeben wurde
        if (session.getAttribute("extra_effort_form") == null && event != null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("extra_effort_form", this.createExtraEffortForm(event, request));
        }

        session.removeAttribute("extra_effort_form");
        
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/enter_extra_efforts_edit.jsp").forward(request, response);
        
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Angeforderte Aktion ausführen
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "saveHelferInEvent":
                //TODO: an MitgliedServlet weiterleiten
                
                // TODO: irgendwie die Liste der ausgewählten Helfer vom MitgliedServlet übermitteln...
                break;
            case "saveEvent":
                this.saveEvent(request, response);
                break;
        }
    }
    
    private void saveEvent (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<String> errors = new ArrayList<String>();
        String titel = request.getParameter("special_efforts_titel");
        String strBeginn = request.getParameter("special_efforts_zeit_beginn");
        String strEnde = request.getParameter("special_efforts_zeit_ende");
        String abteilung = request.getParameter("special_efforts_abteilung");
        
        if (strBeginn == null || strBeginn.trim().isEmpty()) {
            errors.add("Es wurde kein Beginndatum/-zeit eingegeben.");
            strBeginn = "";
        }

        if (strEnde == null || strEnde.trim().isEmpty()) {
            errors.add("Es wurde kein Endedatum/-zeit eingegeben.");
            strEnde = "";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date beginn = null, ende = null;
        try {
            beginn = formatter.parse(strBeginn);
            ende = formatter.parse(strEnde);
        } catch (ParseException ex) {
            errors.add("Ungültige Werte! Bitte Datum und Uhrzeit nach dem Format 'dd.MM.yyyy hh:mm' eintragen.");
            Logger.getLogger(NormalStundenServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (beginn != null && ende != null) {
            if (beginn.after(ende)) {
                errors.add("Das Beginndatum darf nicht später als das Endedatum sein.");
            }

            Event event = new Event(beginn, ende, this.arbeitszeitBean.berechneZeitspanne(beginn, ende), titel, abteilung);
            this.validationBean.validate(event, errors);

            // Neue Arbeitszeit anlegen
            if (errors.isEmpty()) {
                this.eventBean.saveNew(event);
            }
        }
        
        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("effort_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
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
    
    private FormValues createExtraEffortForm(Event event, HttpServletRequest request) {
        Map<String, String[]> values = new HashMap<>();

        values.put("special_efforts_titel", new String[]{
            String.valueOf(event.geteTitel())
        });

        values.put("special_efforts_zeit_beginn", new String[]{
            WebUtils.formatTimestamp(event.getBeginn())
        });

        values.put("special_efforts_zeit_ende", new String[]{
            WebUtils.formatTimestamp(event.getEnde())
        });

        List<Benutzer> helfer = event.getHelfer();
        request.setAttribute("helfer", helfer);
        
        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}    
