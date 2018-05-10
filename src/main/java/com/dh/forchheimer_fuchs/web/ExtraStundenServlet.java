package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.ejb.EventBean;
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
import com.dh.forchheimer_fuchs.jpa.Event;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/extra_efforts/")
public class ExtraStundenServlet extends HttpServlet {

    @EJB
    EventBean eventBean;

    @EJB
    BenutzerBean benutzerBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Arbeitszeiten und Stati für die Suchfelder ermitteln
        request.setAttribute("extra_efforts", this.eventBean.findAllSorted());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("special_effort_titel");
        String datumVon = request.getParameter("special_effort_zeit_beginn");
        String datumBis = request.getParameter("special_effort_zeit_ende");
        
        if (datumVon == null || datumVon.trim().isEmpty()) {
            datumVon = "";
        }

        if (datumBis == null || datumBis.trim().isEmpty()) {
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
        // Anzuzeigende Aufgaben suchen
        List<Event> events = this.eventBean.search(searchText, beginn, ende);
        request.setAttribute("extra_efforts", events);
        
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/enter_extra_efforts.jsp").forward(request, response);
    }
}
