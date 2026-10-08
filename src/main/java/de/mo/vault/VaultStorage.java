package de.mo.vault;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * Speichert und lädt den Tresor in eine Textdatei
 * Version 1: unverschlüsselt, eine Zeile pro Eintrag
 */

public class VaultStorage {

    /**
     * Verschlüsselt den Tresor und schreibt ihn als Base64-Text in die Datei.
     * Pro Zeile im Klartext: Dienst, Benutzername, Passwort, getrennt durch Tabulator.
     */
    public static void save(Vault vault, Path file, SecretKey key)
            throws IOException, GeneralSecurityException {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : vault.getEntries()) {
            sb.append(entry.getService()).append("\t")
                    .append(entry.getUsername()).append("\t")
                    .append(entry.getPassword()).append("\n");
        }
        byte[] encrypted = CryptoService.encrypt(sb.toString(), key);
        Files.writeString(file, Base64.getEncoder().encodeToString(encrypted));
    }

    /**
     * Liest die Datei, entschlüsselt sie und baut den Tresor daraus auf.
     * Existiert die Datei noch nicht, wird ein leerer Tresor zurückgegeben.
     */
    public static Vault load(Path file, SecretKey key)
            throws IOException, GeneralSecurityException {
        Vault vault = new Vault();
        if (!Files.exists(file)) {
            return vault;
        }
        byte[] data = Base64.getDecoder().decode(Files.readString(file).trim());
        String plaintext = CryptoService.decrypt(data, key);
        for (String line : plaintext.split("\n")) {
            String[] parts = line.split("\t");
            if (parts.length == 3) {
                vault.add(new Entry(parts[0], parts[1], parts[2]));
            }
        }
        return vault;
    }
}
