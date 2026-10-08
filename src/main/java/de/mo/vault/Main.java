package de.mo.vault;


import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main (String[] args) throws IOException {


        Vault vault = new Vault();
        vault.add(new Entry("Netflix", "Maxi", "passwort123"));
        vault.add(new Entry("GitHub", "m3xXmo0", "test1234"));

        for (Entry entry : vault.getEntries()){
            System.out.println(entry);
        }
        Entry found = vault.findByService("GitHub");
        System.out.println(found);
        System.out.println(vault.findByService("Spotify"));

        System.out.println(vault.removeByService("Netflix"));
        System.out.println(vault.removeByService("Netflix"));
        System.out.println(vault.getEntries());

        VaultStorage.save(vault, Path.of("vault.txt"));
    }

}
