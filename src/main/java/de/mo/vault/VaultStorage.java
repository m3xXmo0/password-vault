package de.mo.vault;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Speichert und lädt den Tresor in eine Textdatei
 * Version 1: unverschlüsselt, eine Zeile pro Eintrag
 */

public class VaultStorage {

    /**
     * Schreibt alle Einträge des Tresors in die Datei
     * Pro Zeile: Dienst, Benutzername, Passwort, getrennt durch einen Tubulator
     */
    public static void save(Vault vault, Path file) throws IOException{
        List<String> lines = new ArrayList<>();
        for(Entry entry : vault.getEntries()){
            lines.add(entry.getService() + "\t" + entry.getUsername() + "\t" + entry.getPassword());
        }

        Files.write(file, lines);
    }

}
