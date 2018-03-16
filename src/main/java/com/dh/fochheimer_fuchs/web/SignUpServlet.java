/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package com.dh.fochheimer_fuchs.web;


import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
import static com.dh.forchheimer_fuchs.jpa.Benutzer_.admin;
import static com.dh.forchheimer_fuchs.jpa.Benutzer_.mitgliedsnr;
import static com.dh.forchheimer_fuchs.jpa.Person_.hausnr;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {
    
           
    @EJB
    BenutzerBean benutzerBean;
    
    @EJB
    ValidationBean validationBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signup.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signup_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
        
        long mitgliedsnr = Long.parseLong(request.getParameter("signup_mitgliedsnr"));
        String benutzername = request.getParameter("signup_benutzername");
        String passwort1 = request.getParameter("signup_passwort1");
        String passwort2 = request.getParameter("signup_passwort2");
        String nachname = request.getParameter("signup_nachname");
        String vorname = request.getParameter("signup_vorname");
        String strasse = request.getParameter("signup_strasse");
        String hausnr = request.getParameter("signup_hausnummer");
        String plz = request.getParameter("signup_plz");
        String ort = request.getParameter("signup_ort");
        String telefonnr = request.getParameter("signup_telefonnr");                            
        String email = request.getParameter("signup_email");
        String abteilung = request.getParameter("signup_abteilung");
        boolean admin = Boolean.parseBoolean(request.getParameter("signup_admin"));
        
        // Eingaben prüfen
        Benutzer benutzer = new Benutzer(mitgliedsnr, benutzername, nachname, vorname, strasse, hausnr, plz, ort, passwort1, email, telefonnr, abteilung, admin);
        List<String> errors = this.validationBean.validate(benutzer);
        this.validationBean.validate(benutzer.getPasswort(), errors);
        
        if (passwort1 != null && passwort2 != null && !passwort1.equals(passwort2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.benutzerBean.signup(benutzername, passwort1);
            } catch (BenutzerBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(benutzername, passwort1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
}
