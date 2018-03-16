/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Valerie
 */
@Entity
public class Benutzer extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "MITGLIEDSNUMMER", length = 64)
    @Size(min = 5, max = 64, message = "Die Mitgliedsnummer muss zwischen fünf und 64 Zeichen lang sein.")
    @NotNull(message = "Die Mitgliedsnummer darf nicht leer sein.")
    private long mitgliedsnr;
    
    @Column(name = "BENUTZERNAME")
    private String benutzername;
    
    public class Passwort {
        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
        public String passwort = "";
    }
    @Transient
    private final Passwort passwort = new Passwort();
    
    @Column(name = "PASSWORT_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwortHash;

    @ElementCollection
    @CollectionTable(
            name = "FF_USER_GRUPPE",
            joinColumns = @JoinColumn(name = "MITGLIEDSNUMMER")
    )
    @Column(name = "GRUPPENNAME")
    List<String> gruppen = new ArrayList<>();
    
    @Column(name = "EMAIL")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Die E-mail ist nicht valide.")
    @NotNull (message = "Die E-mail darf nicht leer sein.")
    private String email;
    
    @Column(name = "TELEFONNUMMER")
    @Size (min = 9, message = "Die Telefonnummer muss mindestens 9 Zeichen haben.")
    @NotNull (message = "Der Telefonnummer darf nicht leer sein.")
    private String telefonnr;
    
    @Column(name = "ABTEILUNG")
    @NotNull (message = "Die Abteilung darf nicht leer sein.")
    private String abteilung;
    
    @Column(name = "ADMINISTRATOR")
    @NotNull (message = "Die Auswahlmöglichkeit für Administrator-Rechte darf nicht leer sein.")
    private Boolean admin;
    
    @OneToMany
    private ArrayList<Arbeitszeit> arbeitszeit = new ArrayList<>();
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Benutzer() {    
        
    }
    

    
    public Benutzer(long mitgliedsnr, String benutzername, String nachname, String vorname, String strasse, String hausnr, String plz, String ort, String passwort, String email, String telefonnr, String abteilung, Boolean admin) {
        super(nachname, vorname, strasse, hausnr, plz, ort);
        this.mitgliedsnr = mitgliedsnr;
        this.benutzername = benutzername;
        this.passwort.passwort = passwort;
        this.passwortHash = this.hashPasswort(passwort);
        this.email = email;
        this.telefonnr = telefonnr;
        this.abteilung = abteilung;
        this.admin = admin;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public long getMitgliedsnr() {
        return mitgliedsnr;
    }

    public void setMitgliedsnr(long mitgliedsnr) {
        this.mitgliedsnr = mitgliedsnr;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefonnr() {
        return telefonnr;
    }

    public void setTelefonnr(String telefonnr) {
        this.telefonnr = telefonnr;
    }
    
    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }
    
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    public ArrayList<Arbeitszeit> getArbeitszeit() {
        return arbeitszeit;
    }

    public void setArbeitszeit(ArrayList<Arbeitszeit> arbeitszeit) {
        this.arbeitszeit = arbeitszeit;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet der Hash-Wert zu einem Passwort.
     *
     * @param passwort Passwort
     * @return Hash-Wert
     */
    private String hashPasswort(String passwort) {
        byte[] hash;

        if (passwort == null) {
            passwort = "";
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(passwort.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        BigInteger bigInt = new BigInteger(1, hash);
        return bigInt.toString(16);
    }

    /**
     * Berechnet einen Hashwert aus dem übergebenen Passwort und legt ihn im
     * Feld passwordHash ab. Somit wird das Passwort niemals als Klartext
     * gespeichert.
     * 
     * Gleichzeitig wird das Passwort im nicht gespeicherten Feld password
     * abgelegt, um durch die Bean Validation Annotationen überprüft werden
     * zu können.
     *
     * @param passwort Neues Passwort
     */
    public void setPasswort(String passwort) {
        this.passwort.passwort = passwort;
        this.passwortHash = this.hashPasswort(passwort);
    }

    /**
     * Nur für die Validierung bei einer Passwortänderung!
     * @return Neues, beim Speichern gesetztes Passwort
     */
    public Passwort getPasswort() {
        return this.passwort;
    }
    
    /**
     * Prüft, ob das übergebene Passwort korrekt ist.
     *
     * @param passwort Zu prüfendes Passwort
     * @return true wenn das Passwort stimmt sonst false
     */
    public boolean checkPasswort(String passwort) {
        return this.passwortHash.equals(this.hashPasswort(passwort));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Zuordnung zu Benutzergruppen">
    /**
     * @return Eine unveränderliche Liste aller Benutzergruppen
     */
    public List<String> getGruppen() {
        List<String> gruppenListe = new ArrayList<>();
        
        for (String gruppenname : this.gruppen){
            gruppenListe.add(gruppenname);
        }

        return gruppenListe;
    }

    /**
     * Fügt den Benutzer einer weiteren Benutzergruppe hinzu.
     *
     * @param gruppenname Name der Benutzergruppe
     */
    public void addToGroup(String gruppenname) {
        if (!this.gruppen.contains(gruppenname)) {
            this.gruppen.add(gruppenname);
        }
    }

    /**
     * Entfernt den Benutzer aus der übergebenen Benutzergruppe.
     *
     * @param gruppenname Name der Benutzergruppe
     */
    public void removeFromGroup(String gruppenname) {
        this.gruppen.remove(gruppenname);
    }
    //</editor-fold>
}
