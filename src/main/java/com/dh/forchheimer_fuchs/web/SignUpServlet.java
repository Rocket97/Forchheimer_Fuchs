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


import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import com.dh.forchheimer_fuchs.ejb.ValidationBean;
import com.dh.forchheimer_fuchs.jpa.Benutzer;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/signup.jsp");
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
        long mitgliedsnr;
        String mnr = request.getParameter("signup_mitgliedsnr");
        if (mnr.equals("")){
            mitgliedsnr = 0;
        } else {
            mitgliedsnr = Long.parseLong(mnr);
        }
        String benutzername = request.getParameter("signup_benutzername");
        String nachname = request.getParameter("signup_nachname");
        String vorname = request.getParameter("signup_vorname");
        String strasse = request.getParameter("signup_strasse");
        String hausnr = request.getParameter("signup_hausnummer");
        String plz = request.getParameter("signup_plz");
        String ort = request.getParameter("signup_ort");
        String telefonnr = request.getParameter("signup_telefonnummer");                            
        String email = request.getParameter("signup_email");
        String abteilung = request.getParameter("signup_abteilung_jugend") + "," + request.getParameter("signup_abteilung_bereitschaft");                    // hier müssen wir nochmal schauen, wegen Auslesen des Wertes
        boolean admin = Boolean.parseBoolean(request.getParameter("signup_admin"));
        
        // alle leeren Strings auf null setzen, sodass kein leerer String abgespeichert wird
        if(benutzername.equals("")){
            benutzername = null;
        }
        if(nachname.equals("")){
            nachname = null;
        }
        if(vorname.equals("")){
            vorname = null;
        }
        if(strasse.equals("")){
            strasse = null;
        }
        if(hausnr.equals("")){
            hausnr = null;
        }
        if(plz.equals("")){
            plz = null;
        }
        if(ort.equals("")){
            ort = null;
        }
        if(telefonnr.equals("")){
            telefonnr = null;
        }
        if(email.equals("")){
            email = null;
        }
        if(abteilung.equals("")){
            abteilung = null;
        }
        
        // Eingaben prüfen
        Benutzer benutzer = new Benutzer(mitgliedsnr, benutzername, benutzername, nachname, vorname, strasse, hausnr, plz, ort, email, telefonnr, abteilung, admin);
        List<String> errors = this.validationBean.validate(benutzer);
        this.validationBean.validate(benutzer.getPasswort(), errors);
        
        if (mitgliedsnr == 0){
            errors.add("Die Mitgliedsnummer darf nicht leer sein und darf nicht 0 sein.");
        }
        
        if (abteilung.equals("null,null")){
            errors.add("Der Nutzer muss mindestens einer Abteilung zugeordnet sein.");
        }
        
        // Meldung, dass Passwort nicht gegeben wurde, weil kein Benutzername eingegeben wurde, aus errors rausnehmen
        if(errors.contains("Der Benutzername muss zwischen 6 und 64 Zeichen lang sein.")){
            errors.remove("Der Benutzername muss zwischen 6 und 64 Zeichen lang sein.");
        }
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.benutzerBean.registrieren(benutzer);
            } catch (BenutzerBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
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
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
}
