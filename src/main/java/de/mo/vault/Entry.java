package de.mo.vault;
/**
 * Ein einzelner Eintrag im Passwort-Tresor,
 * z. B. der Zugang zu einem Dienst wie GitHub.
 */

public class Entry {
    private String service;   // Name des Dienstes. z.B. "GitHub"
    private String username;   // Benutzername bzw. Email für diesen Dienst
    private String password;   // Passwort (In Version 1 noch unverschlüsselt)

    //Konstruktor:
    public Entry(String service, String username, String password){
        // this.(...) ist das Feld des Objekts. (...) allein ist der Parameter
        this.service = service;
        this.username = username;
        this.password = password;
    }

    public String getService() {
        return service; // Gibt den Namen des Dienstes zurück
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    /**
     * Gibt den Eintrag als "Dienst (Benutzername)" zurück.
     * Das Passwort steht nicht drin, weil 'toString()'
     * automatisch aufgerufen werden kann (bei println oder in Logs) -> Passwort Leak -> Schlecht...
     */
    @Override
    public String toString(){
        return service +  " (" + username + ")";
    }





}
