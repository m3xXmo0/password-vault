package de.mo.vault;

import javax.crypto.AEADBadTagException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;


/**
 * Speichert und lädt den Tresor verschlüsselt
 * (AES-GCM, Schlüssel aus Master-Passwort per PBKDF2)
 */

public class VaultStorage {

    /**
     * Leitet aus dem Master-Passwort einen Schlüssel ab, verschlüsselt den Tresor
     * und schreibt Salt + verschlüsselte Daten als Base64 in die Datei.
     */
    public static void save(Vault vault, Path file, char[] password)
            throws IOException, GeneralSecurityException {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : vault.getEntries()) {
            sb.append(entry.getService()).append("\t")
                    .append(entry.getUsername()).append("\t")
                    .append(entry.getPassword()).append("\n");
        }
        byte[] salt = CryptoService.generateSalt();
        SecretKey key = CryptoService.deriveKey(password, salt);
        byte[] encrypted = CryptoService.encrypt(sb.toString(), key);

        byte[] result = new byte[salt.length + encrypted.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(encrypted, 0, result, salt.length, encrypted.length);
        Files.writeString(file, Base64.getEncoder().encodeToString(result));
    }

    /**
     * Liest die Datei -> trennt das Salt ab -> leitet den Schlüssel aus dem
     * Master-Passwort ab und entschlüsselt den Tresor.
     * Existiert die Datei noch nicht, wird ein leerer Tresor zurückgegeben.
     */
    public static Vault load(Path file, char[] password)
            throws IOException, VaultException {
        Vault vault = new Vault();
        if (!Files.exists(file)) {
            return vault;
        }
        try {
            byte[] data = Base64.getDecoder().decode(Files.readString(file).trim());
            if (data.length < CryptoService.SALT_LENGTH) {
                throw new VaultException("Die Tresor-Datei ist beschädigt (zu kurz)");
            }
            byte[] salt = Arrays.copyOfRange(data, 0, CryptoService.SALT_LENGTH);
            byte[] encrypted = Arrays.copyOfRange(data, CryptoService.SALT_LENGTH, data.length);

            SecretKey key = CryptoService.deriveKey(password, salt);
            String plaintext = CryptoService.decrypt(encrypted, key);
            for (String line : plaintext.split("\n")) {
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    vault.add(new Entry(parts[0], parts[1], parts[2]));
                }
            }
        } catch (AEADBadTagException e) {
            throw new VaultException("Falsches Master-Passwort oder manipulierte Datei");
        } catch (IllegalArgumentException e) {
            throw new VaultException("Die Tresor-Datei ist beschädigt (kein gültiges Base64)");
        } catch (GeneralSecurityException e) {
            throw new VaultException("Entschlüsselung fehlgeschlagen: " + e.getMessage());
        }
        return vault;

    }
}
