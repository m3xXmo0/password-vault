package de.mo.vault;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main (String[] args) throws Exception {

        Path file = Path.of("vault.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Master-Kennwort: ");
        char[] masterPassword = scanner.nextLine().toCharArray();
        Vault vault;
        try {
            vault = VaultStorage.load(file, masterPassword);
        } catch (VaultException e) {
            System.out.println("Fehler: " + e.getMessage());
            return;
        }



        boolean running = true;
        while(running){
            System.out.println();
            System.out.println("1) Alle Einträge anzeigen");
            System.out.println("2) Eintrag Hinzufügen");
            System.out.println("3) Eintrag löschen");
            System.out.println("4) Master-Passwort ändern");
            System.out.println("5) Beenden");;
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

                    System.out.print("Benutzername: ");
                    String username = scanner.nextLine();

                    System.out.print("Passwort (oder g für generieren): ");
                    String password = scanner.nextLine();

                    if (password.equals("g")) {
                        password = PasswordGenerator.generate(16);
                        System.out.println("Generiert: " + password);
                    }

                    vault.add(new Entry(service, username, password ));
                    VaultStorage.save(vault, file, masterPassword);

                    System.out.println("Eintrag gespeichert");
                    break;

                case "3":
                    System.out.print("Dienst, der gelöscht werden soll: ");
                    String serviceDelete = scanner.nextLine();
                    if(vault.removeByService(serviceDelete)){
                        VaultStorage.save(vault, file, masterPassword);
                        System.out.println("Dienst gelöscht");
                    } else {
                        System.out.println("Nicht gefunden");
                    }
                    break;

                case "4":
                    System.out.print("Aktuelles Master-Passwort: ");
                    char[] current = scanner.nextLine().toCharArray();
                    if (!Arrays.equals(current, masterPassword)) {
                        System.out.println("Falsches Passwort.");
                        break;
                    }
                    System.out.print("Neues Master-Passwort: ");
                    char[] newPassword = scanner.nextLine().toCharArray();
                    System.out.print("Neues Master-Passwort wiederholen: ");
                    char[] repeat = scanner.nextLine().toCharArray();
                    if (newPassword.length == 0 || !Arrays.equals(newPassword, repeat)) {
                        System.out.println("Eingaben stimmen nicht überein.");
                        break;
                    }
                    VaultStorage.save(vault, file, newPassword);
                    masterPassword = newPassword;
                    System.out.println("Master-Passwort geändert.");
                    break;

                case "5":
                    running = false;
                    break;

                default:
                    System.out.println("Ungültige Auswahl!");
            }
        }


        scanner.close();
    }

}
