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
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import java.io.IOException;
import java.util.List;
import javafx.concurrent.Task;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anzeigen und Bearbeiten der Arbeitsstunden. Die Seite besitzt ein
 * Formular, mit dem ein neue Kategorie angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Kategorien verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/categories/"})
public class StundenAnlegenServlet extends HttpServlet {

    @EJB
    ArbeitszeitBean arbeitszeitBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Arbeitszeiten ermitteln
        request.setAttribute("arbeitszeiten", this.arbeitszeitBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/enter_efforts.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("arbeitszeit_form");
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
            case "create":
                this.createArbeitszeit(request, response);
                break;
            case "delete":
                this.deleteCategories(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Arbeitszeit anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createArbeitszeit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Arbeitszeit arbeitszeit = new Arbeitszeit();
        List<String> errors = this.validationBean.validate(arbeitszeit);

        // Neue Arbeitszeit anlegen
        if (errors.isEmpty()) {
            this.arbeitszeitBean.saveNew(arbeitszeit);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Arbeitszeit löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] zeitIds = request.getParameterValues("arbeitszeit");

        if (zeitIds == null) {
            zeitIds = new String[0];
        }

        // Kategorien löschen
        for (String zeitId : zeitIds) {
            // Zu löschende Kategorie ermitteln
            Arbeitszeit arbeitszeit;

            try {
                arbeitszeit = this.arbeitszeitBean.findById(Long.parseLong(zeitId));
            } catch (NumberFormatException ex) {
                continue;
            }
            
            if (arbeitszeit == null) {
                continue;
            }
            // Und weg damit
            this.arbeitszeitBean.delete(arbeitszeit);
        }
        
        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }

}
