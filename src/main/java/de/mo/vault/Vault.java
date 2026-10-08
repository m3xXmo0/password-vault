package de.mo.vault;
import java.util.ArrayList;
import java.util.List;

/** Vault ist die Klasse, die Einträge des Passwort-Tresors
 * verwaltet. (Hinzufügen, abrufen, auflisten, löschen)
 */

public class Vault {
    private final List<Entry> entries;

    public Vault(){
        this.entries = new ArrayList<>();
    }

    // Fügt einen Eintrag zum Tresor hinzu
    public void add(Entry entry){
        entries.add(entry);
    }
    /**
     * Gibt alle Einträge des Tresors zurück
     */
    public List<Entry> getEntries(){
        return List.copyOf(entries);
    }
}
