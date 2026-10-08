package de.mo.vault;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {


    public static void main (String[] args) throws IOException {

        Path file = Path.of("vault.txt");
        Vault vault = VaultStorage.load(file);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while(running){
            System.out.println();
            System.out.println("(1) Alle Einträge anzeigen");
            System.out.println("(2) Beenden");
            System.out.println("Auswahl: ");
            String eingabe = scanner.nextLine();

            switch (eingabe){
                case "1":
                    for(Entry entry : vault.getEntries()){
                        System.out.println(entry);
                    } break;

                case "2":
                    running = false;
                    break;

                default:
                    System.out.println("Ungültige Auswahl!");
            }
        }


        scanner.close();
    }

}
