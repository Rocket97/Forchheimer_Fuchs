package com.dh.forchheimer_fuchs.jpa;

/**
 * Statuswerte einer Aufgabe.
 */
public enum ProtokollKategorie {
    NOTFALLHILFE, SANITÄTSDIENST;
    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case NOTFALLHILFE:
                return "Notfallhilfe";
            case SANITÄTSDIENST:
                return "Sanitätsdienst";
            default:
                return this.toString();
        }
    }
}
