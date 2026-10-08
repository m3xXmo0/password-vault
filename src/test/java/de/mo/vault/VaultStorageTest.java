package de.mo.vault;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VaultStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadReturnsSameEntries() throws Exception {
        Path file = tempDir.resolve("vault.dat");
        Vault vault = new Vault();
        vault.add(new Entry("GitHub", "mo", "pw1"));
        vault.add(new Entry("Netflix", "maxi", "pw2"));

        VaultStorage.save(vault, file, "master".toCharArray());
        Vault loaded = VaultStorage.load(file, "master".toCharArray());

        assertEquals(2, loaded.getEntries().size());
        assertEquals("pw2", loaded.findByService("Netflix").getPassword());
    }

    @Test
    void wrongMasterPasswordIsRejected() throws Exception {
        Path file = tempDir.resolve("vault.dat");
        VaultStorage.save(new Vault(), file, "richtig".toCharArray());

        assertThrows(VaultException.class,
                () -> VaultStorage.load(file, "falsch".toCharArray()));
    }

    @Test
    void manipulatedFileIsDetected() throws Exception {
        Path file = tempDir.resolve("vault.dat");
        Vault vault = new Vault();
        vault.add(new Entry("GitHub", "mo", "pw1"));
        VaultStorage.save(vault, file, "master".toCharArray());

        String content = Files.readString(file);
        char[] chars = content.toCharArray();
        int pos = chars.length / 2;
        chars[pos] = (chars[pos] == 'A') ? 'B' : 'A';   // ein Zeichen in der Mitte ändern
        Files.writeString(file, new String(chars));

        assertThrows(VaultException.class,
                () -> VaultStorage.load(file, "master".toCharArray()));
    }

    @Test
    void tooShortFileIsRejected() throws Exception {
        Path file = tempDir.resolve("vault.dat");
        Files.writeString(file, "AAAA");

        assertThrows(VaultException.class,
                () -> VaultStorage.load(file, "master".toCharArray()));
    }

    @Test
    void missingFileGivesEmptyVault() throws Exception {
        Path file = tempDir.resolve("gibt-es-nicht.dat");
        Vault loaded = VaultStorage.load(file, "master".toCharArray());
        assertEquals(0, loaded.getEntries().size());
    }
}