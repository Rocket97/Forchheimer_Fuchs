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
@WebServlet(urlPatterns = "/user/*")
public class UserEditServlet extends HttpServlet {

    @EJB
    BenutzerBean benutzerBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();
        
        // wenn User sein Profil bearbeiten will, sollen seine eigene Daten in den Feldern vorbelegt werden
        // wenn ein Admin einen anderen Benutzer bearbeiten will, dann sollen dessen Daten in den Feldern vorbelegt werden
        Benutzer user = this.getRequestedUser(request);
       
        if (session.getAttribute("user_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("user_form", this.createUserForm(user));
        }
        
         request.setAttribute("admin", benutzerBean.getCurrentUser().getAdmin());
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp").forward(request, response);

        session.removeAttribute("user_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Aktion ausführen
        request.setCharacterEncoding("utf-8");
        
        this.saveUser(request, response);
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formularwerte auslesen
        Long mitgliedsnr = Long.parseLong(request.getParameter("user_mitgliedsnummer"));
        String benutzername = request.getParameter("user_username");
        String passwordAlt = request.getParameter("user_passwordAlt");
        String password1 = request.getParameter("user_password1");
        String password2 = request.getParameter("user_password2");
        String vorname = request.getParameter("user_vorname");
        String nachname = request.getParameter("user_nachname");
        String strasse = request.getParameter("user_strasse");
        String hausnummer = request.getParameter("user_hausnummer");
        String plz = request.getParameter("user_plz");
        String ort = request.getParameter("user_ort");
        String email = request.getParameter("user_email");
        String telefonnummer = request.getParameter("user_telefonnummer");
        String abteilung = request.getParameter("user_abteilung");
        Boolean admin = Boolean.parseBoolean(request.getParameter("user_admin"));
        
        // eingegebene Werte prüfen
        Benutzer userNeu = new Benutzer (mitgliedsnr, benutzername, password1, nachname, vorname, strasse, hausnummer, plz, ort, email, telefonnummer, abteilung, admin);
        
        if (!password1.equals("")){
            userNeu = new Benutzer (mitgliedsnr, benutzername, password1, nachname, vorname, strasse, hausnummer, plz, ort, email, telefonnummer, abteilung, admin);
        }
        List<String> errors = this.validationBean.validate(userNeu);
        this.validationBean.validate(userNeu.getPasswort(), errors);
        
        // altes Passwort muss eingegeben werden
        if (passwordAlt != null && passwordAlt.equals(this.benutzerBean.getCurrentUser().getPasswort().toString())){
            errors.add("Das alte Passwort stimmt nicht.");
        }
        
        // Neues Passwort und dessen Wiederholung auf Gleichheit überprüfen
        // Neue Passwörter dürfen auch leer sein, wenn man sein Passwort nicht ändern möchte
        if (!password1.equals(password2)) {
            errors.add("Die beiden neuen Passwörter stimmen nicht überein.");
        }
        
        // Datensatz updaten und wenn das Passwort geändert wird, auch das Passwort separat ändern, 
        // um leere Strings als Passwörter abfangen zu können
        Benutzer userAlt = this.benutzerBean.getCurrentUser();
        if (errors.isEmpty() && !password1.equals("")) {
            // hier: nicht mehr nötig, auf altes Passwort zu überprüfen, weil das in Zeile 106 schon gemacht wurde
            try {
                this.benutzerBean.aenderePasswort(userAlt, passwordAlt, password1);
            }   catch (BenutzerBean.InvalidCredentialsException ex) {
                errors.add("Das Passwort konnte nicht verändert werden.");
            }
        }
        
        //Wenn beim Passwort alles gut gegangen ist, aktualisiere den Rest
        if (errors.isEmpty()){
            userAlt.setMitgliedsnr(mitgliedsnr);
            userAlt.setVorname(vorname);
            userAlt.setNachname(nachname);
            userAlt.setStrasse(strasse);
            userAlt.setHausnr(hausnummer);
            userAlt.setPlz(plz);
            userAlt.setOrt(ort);
            userAlt.setEmail(email);
            userAlt.setTelefonnr(telefonnummer);
            userAlt.setAbteilung(abteilung);
            userAlt.setAdmin(admin);
            
            this.benutzerBean.aktualisieren(userAlt);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/home/"));
        } else {
            // Fehler: Formular erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("user_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }
    
    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Benutzer getRequestedUser(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        

        // ID aus der URL herausschneiden
        String userId = request.getPathInfo();

        if (userId == null) {
            userId = "";
        }

        userId = userId.substring(1);

        if (userId.endsWith("/")) {
            userId = userId.substring(0, userId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
//       try{
            Benutzer user = this.benutzerBean.findById(userId);
//        } catch (NumberFormatException ex) {
//            // Ungültige oder keine ID in der URL enthalten
//        }

        return user;
    }
    
    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param user Der zu bearbeitende Benutzer
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createUserForm(Benutzer user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("user_mitgliedsnummer", new String[]{
            String.valueOf(user.getMitgliedsnr())
        });
        
        values.put("user_username", new String[]{
            user.getBenutzername()
        });

        values.put("user_vorname", new String[]{
            user.getVorname()
        });

        values.put("user_nachname", new String[]{
           user.getNachname()
        });

        values.put("user_strasse", new String[]{
            user.getStrasse()
        });

        values.put("user_hausnummer", new String[]{
            user.getHausnr()
        });

        values.put("user_plz", new String[]{
            user.getPlz()
        });
        
        values.put("user_ort", new String[]{
            user.getOrt()
        });
        
        values.put("user_email", new String[]{
            user.getEmail()
        });
        
        values.put("user_telefonnummer", new String[]{
            user.getTelefonnr()
        });
        
        values.put("user_abteilung", new String[]{
            user.getAbteilung()
        });
        
        values.put("user_admin", new String[]{
            String.valueOf(user.getAdmin())
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}
