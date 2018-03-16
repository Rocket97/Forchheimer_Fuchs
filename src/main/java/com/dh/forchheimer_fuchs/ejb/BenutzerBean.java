/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Benutzer;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Valerie
 */
@Stateless
public class BenutzerBean{

    @PersistenceContext
    EntityManager em;
    
    @Resource
    EJBContext context;

    /**
     * Gibt das Datenbankobjekt des aktuell eingeloggten Benutzers zurück,
     *
     * @return Eingeloggter Benutzer oder null
     */
    public Benutzer getCurrentUser() {
        return this.em.find(Benutzer.class, this.context.getCallerPrincipal().getName());
    }

    /**
     *
     * @param mitgliedsnr
     * @param benutzername
     * @param passwort
     * @param vorname
     * @param nachname
     * @param strasse
     * @param hausnr
     * @param plz
     * @param ort
     * @param email
     * @param telefonnr
     * @param abteilung
     * @param admin
     * @throws BenutzerBean.UserAlreadyExistsException
     */
    
    @RolesAllowed("ff_admin")
    public void registrieren(long mitgliedsnr, String benutzername, String passwort, String vorname, String nachname, String strasse, String hausnr, String plz, String ort, String email, String telefonnr, String abteilung, Boolean admin) throws UserAlreadyExistsException {
        if (em.find(Benutzer.class, mitgliedsnr) != null) {
            String mnr = String.valueOf(mitgliedsnr);
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", mnr));
        }

        Benutzer user = new Benutzer(mitgliedsnr, benutzername, passwort, nachname, vorname, strasse, hausnr, plz, ort, email, telefonnr, abteilung, admin);
        if (user.getAdmin()){
            user.addToGroup("ff_admin");
        } 
        user.addToGroup("ff_nutzer");
   
        em.persist(user);
    }

    /**
     * Passwort ändern (ohne zu speichern)
     * @param benutzer
     * @param altesPasswort
     * @param neuesPasswort
     * @throws BenutzerBean.InvalidCredentialsException
     */
    @RolesAllowed("ff_nutzer")
    public void aenderePasswort(Benutzer benutzer, String altesPasswort, String neuesPasswort) throws InvalidCredentialsException {
        if (benutzer == null || !benutzer.checkPasswort(altesPasswort)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        benutzer.setPasswort(neuesPasswort);
    }
    
    @RolesAllowed("ff_admin")
    public void setzePasswortVonBenutzerZurück(Benutzer benutzer) throws InvalidCredentialsException {
        if (benutzer == null) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        benutzer.setPasswort(benutzer.getBenutzername());
    }
    
    /**
     * Benutzer löschen
     * @param benutzer Zu löschender Benutzer
     */
    @RolesAllowed("ff_nutzer")
    public void löschen(Benutzer benutzer) {
        this.em.remove(benutzer);
    }
    
    /**
     * Benutzer aktualisieren
     * @param benutzer Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed("ff_nutzer")
    public Benutzer aktualisieren(Benutzer benutzer) {
        return em.merge(benutzer);
    }

    public void signup(String username, String password1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Fehler: Der Benutzername ist bereits vergeben
     */
    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Fehler: Das übergebene Passwort stimmt nicht mit dem des Benutzers
     * überein
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
