package de.mo.vault;

import java.io.IOException;
import java.nio.file.Path;

public class Main {


    public static void main (String[] args) throws IOException {


        Path file = Path.of("vault.txt");
        Vault vault = VaultStorage.load(file);

        System.out.println("Geladen: " + vault.getEntries());

        vault.add(new Entry ("Spotify", "Kevin", "testpw1928"));
        VaultStorage.save(vault, file);


    }

}
