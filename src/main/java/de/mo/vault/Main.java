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
            System.out.println("1) Alle Einträge anzeigen");
            System.out.println("2) Eintrag Hinzufügen");
            System.out.println("3) Eintrag löschen");
            System.out.println("4) Beenden");;
            System.out.print("Auswahl: ");
            String input = scanner.nextLine();

            switch (input){
                case "1":
                    for(Entry entry : vault.getEntries()){
                        System.out.println(entry);
                    } break;

                case "2":
                    System.out.print("Dienst: ");
                    String service = scanner.nextLine();

                    System.out.println("Benutzername: ");
                    String username = scanner.nextLine();

                    System.out.println("Passwort: ");
                    String password = scanner.nextLine();
                    vault.add(new Entry(service, username, password ));
                    VaultStorage.save(vault, file);
                    System.out.println("Eintrag gespeichert");
                    break;

                case "3":
                    System.out.print("Dienst, der gelöscht werden soll: ");
                    String serviceDelete = scanner.nextLine();
                    if(vault.removeByService(serviceDelete)){
                        VaultStorage.save(vault, file);
                        System.out.println("Dienst gelöscht");
                    } else {
                        System.out.println("Nicht gefunden");
                    }
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    System.out.println("Ungültige Auswahl!");
            }
        }


        scanner.close();
    }

}
