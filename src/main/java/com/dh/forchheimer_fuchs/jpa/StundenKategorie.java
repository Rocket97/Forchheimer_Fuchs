package com.dh.forchheimer_fuchs.jpa;

/**
 * Statuswerte einer Aufgabe.
 */
public enum StundenKategorie {
    AUS_UND_WEITERBILDUNG, HAUSINSTANDHALTUNG, VERWALTUNGSARBEIT, MATERIALPFLEGE, FAHRZEUGWARTUNG, JRK_VERWALTUNGSARBEIT, NOTFALLDARSTELLUNG, SCHULSANITÄTSDIENST;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case AUS_UND_WEITERBILDUNG:
                return "Aus-und Weiterbildung";
            case HAUSINSTANDHALTUNG:
                return "Hausinstandhaltung";
            case VERWALTUNGSARBEIT:
                return "Verwaltungsarbeit";
            case MATERIALPFLEGE:
                return "Materialpflege";
            case FAHRZEUGWARTUNG:
                return "Fahrzeugwartung";
            case JRK_VERWALTUNGSARBEIT:
                return "JRK-Verwaltungsarbeit";
            case NOTFALLDARSTELLUNG:
                return "Notfalldarstellung";
            case SCHULSANITÄTSDIENST:
                return "Schulsanitätsdienst";
            default:
                return this.toString();
        }
    }
}
