/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import com.dh.forchheimer_fuchs.jpa.StundenKategorie;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/stundenAnlegen/*")
public class SundenBearbeitenServlet extends HttpServlet {

    @EJB
    ArbeitszeitBean arbeitszeitBean;

    @EJB
    BenutzerBean benutzerBean;
    
    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Arbeitszeiten und Stati für die Suchfelder ermitteln
        request.setAttribute("arbeitszeit", this.arbeitszeitBean.findAllSorted(this.benutzerBean.getCurrentUser()));
        request.setAttribute("kategorie", StundenKategorie.values());

        // Zu bearbeitende Arbeitszeit einlesen
        HttpSession session = request.getSession();

        Arbeitszeit arbeitszeit = this.getRequestedTask(request);
        request.setAttribute("edit", arbeitszeit.getZeitId() != 0);
                                
        if (session.getAttribute("task_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("task_form", this.createTaskForm(arbeitszeit));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/enter_efforts.jsp").forward(request, response);

        session.removeAttribute("task_form");
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
            case "save":
                this.saveTask(request, response);
                break;
            case "delete":
                this.deleteTask(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

     
       
        String taskBeginn = request.getParameter("task_Beginn");
        String taskEnde = request.getParameter("task_ende");
        StundenKategorie taskKategorie =StundenKategorie.valueOf(request.getParameter("task_kategorie"));
      
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM d HH:mm:ss z uuuu" );
        ZonedDateTime beginn = ZonedDateTime.parse( taskBeginn , f );
        ZonedDateTime ende = ZonedDateTime.parse( taskBeginn , f );
        
        
        Arbeitszeit arbeitszeit = this.getRequestedTask(request);

        if (taskBeginn != null && !taskBeginn.trim().isEmpty()) {
            try {
                arbeitszeit.setBeginn(beginn);
            } catch (NumberFormatException ex) {
                // Ungültige oder keine Zeit mitgegeben
            }
        }
        if (taskEnde != null && !taskEnde.trim().isEmpty()) {
            try {
                arbeitszeit.setEnde(ende);
            } catch (NumberFormatException ex) {
                // Ungültige oder keine Zeit mitgegeben
            }
        }
         if (taskKategorie != null) {
            try {
                arbeitszeit.setKategorie(taskKategorie);
            } catch (NumberFormatException ex) {
                // Ungültige oder keine Kategorie mitgegeben
            }
        }
        this.validationBean.validate(arbeitszeit, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.arbeitszeitBean.update(arbeitszeit);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/home/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("task_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Arbeitszeit arbeitszeit = this.getRequestedTask(request);
        this.arbeitszeitBean.delete(arbeitszeit);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/home/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Arbeitszeit getRequestedTask(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Arbeitszeit arbeitszeit = new Arbeitszeit();
        arbeitszeit.setHelfer(this.benutzerBean.getCurrentUser());
        arbeitszeit.setBeginn(new ZonedDateTime(LocalDateTime.now(), ));
        arbeitszeit.setEnde(new Time(System.currentTimeMillis()));
        arbeitszeit.setZeitspanne(this.arbeitszeitBean.berechneZeitspanne((Calendar)request.getAttribute("efforts_zeit_beginn"), (Calendar)request.getAttribute("efforts_zeit_ende")));
        // ID aus der URL herausschneiden
        String arbeitszeitId = request.getPathInfo();

        if (arbeitszeitId == null) {
            arbeitszeitId = "";
        }

        arbeitszeitId = arbeitszeitId.substring(1);

        if (arbeitszeitId.endsWith("/")) {
            arbeitszeitId = arbeitszeitId.substring(0, arbeitszeitId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            arbeitszeit = this.arbeitszeitBean.findById(Long.parseLong(arbeitszeitId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return arbeitszeit;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param task Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createTaskForm(Arbeitszeit arbeitszeit) {
        Map<String, String[]> values = new HashMap<>();

        values.put("task_owner", new String[]{
            arbeitszeit.getHelfer().getBenutzername()
        });
       

        if (arbeitszeit.getKategorie()!= null) {
            values.put("task_kategorie", new String[]{
                arbeitszeit.getKategorie().toString()
            });
        }

        values.put("task_due_date", new String[]{
            WebUtils.formatTime(arbeitszeit.getBeginn())
        });

        values.put("task_due_time", new String[]{
            WebUtils.formatTime(arbeitszeit.getEnde())
        });

        values.put("task_kategorie", new String[]{
            arbeitszeit.getKategorie().toString()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
