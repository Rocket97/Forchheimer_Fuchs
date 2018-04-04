/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Benutzer;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Valerie
 */
@Stateless
public class BenutzerBean extends EntityBean<Benutzer, Long> {

    @Resource
    EJBContext context;

    @EJB
    ValidationBean validationBean;

    public public BenutzerBean() {
        super(Benutzer.class);
    }

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
        if (user.getAdmin()) {
            user.addToGroup("ff_admin");
        }
        user.addToGroup("ff_nutzer");

        em.persist(user);
    }

    /**
     * Passwort ändern (ohne zu speichern)
     *
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
     *
     * @param benutzer Zu löschender Benutzer
     */
    @RolesAllowed("ff_admin")
    public void löschen(Benutzer benutzer) {
        this.em.remove(benutzer);
    }

    /**
     * Benutzer aktualisieren
     *
     * @param benutzer Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed("ff_nutzer")
    public Benutzer aktualisieren(Benutzer benutzer) {
        return em.merge(benutzer);
    }

    @RolesAllowed("ff_nutzer")
    public List<Entity> findByUsername(String benutzer) {
        String select = "SELECT x FROM Benutzer x WHERE Benutzername LIKE :benutzer";
        return em.createQuery(select).setParameter("benutzer", benutzer).getResultList();

    }

    /**
     * Suche nach Benutzer anhand ihrer Mitgliedsnummer, Vorname, Nachname und
     * Benutzername.
     *
     * @param searchMitgliedsnr (optional)
     * @param searchVorname (optional)
     * @param searchNachname (optional)
     * @param searchBenutzername (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Benutzer> search(Long searchMitgliedsnr, String searchVorname, String searchNachname, String searchBenutzername) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        // SELECT u FROM Benutzer u
        CriteriaQuery<Benutzer> query = cb.createQuery(Benutzer.class);
        Root<Benutzer> from = query.from(Benutzer.class);
        query.select(from);

        // WHERE u.mitgliedsnr = searchMitgliedsnr
        if (searchMitgliedsnr == null) {
        } else {
            query.where(cb.equal(from.get("mitgliedsnr"), searchMitgliedsnr));
        }

        // WHERE u.benutzername = searchBenutzername
        if (searchBenutzername != null && !searchBenutzername.trim().isEmpty()) {
            query.where(cb.equal(from.get("benutzername"), searchBenutzername));
        }

        // WHERE u.vorname = searchVorname
        if (searchVorname != null && !searchVorname.trim().isEmpty()) {
            query.where(cb.equal(from.get("vorname"), searchVorname));
        }

        // WHERE u.nachname = searchNachname
        if (searchNachname != null && !searchNachname.trim().isEmpty()) {
            query.where(cb.equal(from.get("nachname"), searchNachname));
        }

        return em.createQuery(query).getResultList();
    }

    public void adminAnlegenWennNichtVorhanden() {
        List<Benutzer> admins = em.createQuery("SELECT b FROM Benutzer b WHERE b.admin = True").getResultList();

        if (admins.isEmpty()) {
            Benutzer user = new Benutzer(1, "admin", "admin", "Administrator", "Willy", "Root Str.", "7", "76189", "Karlsruhe", "admin@admin.de", "0721/123456", "IT-Support", true);
            user.addToGroup("ff_admin");
            user.addToGroup("ff_nutzer");

            List<String> errors = validationBean.validate(user);

            if (errors.isEmpty()) {
                em.persist(user);
            }
        }
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
