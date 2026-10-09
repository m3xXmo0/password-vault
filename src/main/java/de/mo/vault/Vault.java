
package de.mo.vault;
import java.util.ArrayList;
import java.util.List;


/** Vault ist die Klasse, die Einträge des Passwort-Tresors
 * verwaltet. (Hinzufügen, abrufen, auflisten, löschen)
 */

public class Vault {
    private final List<Entry> entries;

    public Vault() {
        this.entries = new ArrayList<>();
    }

    // Fügt einen Eintrag zum Tresor hinzu
    public void add(Entry entry) {
        if (entry == null){
            throw new IllegalArgumentException("Eintrag darf nicht null sein");
        }
        entries.add(entry);
    }

    /**
     * Gibt alle Einträge des Tresors zurück
     */

    public List<Entry> getEntries() {
        return List.copyOf(entries);
    }

    // Sucht den ersten Eintrag mit dem angegebenen Dienstnamen
    // Gibt null zurück, wenn es keinen solchen Eintrag gibt

    public Entry findByService(String service) {
        for (Entry entry : entries) {
            if (entry.getService().equalsIgnoreCase(service)) {
                return entry;
            }
        }
        return null;
    }


    //  Löscht den ersten Eintrag mit dem angegebenen Dienstnamen
      // Gibt true zurück, wenn etwas gelöscht wurde, sonst false

    public boolean removeByService(String service) {

        Entry entry = findByService(service);
        if (entry == null) {
            return false;
        }

        entries.remove(entry);
        return true;
    }



}

