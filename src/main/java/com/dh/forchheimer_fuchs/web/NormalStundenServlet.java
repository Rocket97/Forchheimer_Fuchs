
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import com.dh.forchheimer_fuchs.jpa.StundenKategorie;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(urlPatterns = {"/efforts/"})
public class NormalStundenServlet extends HttpServlet {

    @EJB
    ArbeitszeitBean arbeitszeitBean;

    @EJB
    ValidationBean validationBean;
    
    @EJB
    BenutzerBean benutzerBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", StundenKategorie.values());

        // Alle vorhandenen Arbeitszeiten ermitteln
        request.setAttribute("efforts", this.arbeitszeitBean.findAllSorted(this.benutzerBean.getCurrentUser()));
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/enter_efforts.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("effort_form");
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
                this.createArbeitszeit(request, response);
                break;
            case "delete":
                this.deleteArbeitszeit(request, response);
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
        StundenKategorie kategorie = StundenKategorie.valueOf(request.getParameter("effort_category"));
        String datumVon = request.getParameter("efforts_zeit_beginn");
        String datumBis = request.getParameter("efforts_zeit_ende");
        Benutzer user = this.benutzerBean.getCurrentUser();
        
        
        
        //Zeitspanne berechnen
        if (datumVon == null) {
            datumVon = "";
        }
        
        if (datumBis == null) {
            datumBis = "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date beginn = null, ende = null;
        try {
            beginn = formatter.parse(datumVon);
            ende = formatter.parse(datumBis);
        } catch (ParseException ex) {
            Logger.getLogger(NormalStundenServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        Arbeitszeit arbeitszeit = new Arbeitszeit(user, kategorie, beginn, ende, this.arbeitszeitBean.berechneZeitspanne(beginn, ende));
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
            session.setAttribute("effort_form", formValues);
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
    private void deleteArbeitszeit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] zeitIds = request.getParameterValues("effort");

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
