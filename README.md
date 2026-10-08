# Password Vault

Ein Passwort-Tresor für die Kommandozeile (Java-only). 
Lernprojekt: Ich wollte verstehen, wie symmetrische Verschlüsselung,
Schlüsselableitung und Manipulationserkennung in der Praxis zusammenspielen.

## Funktionen

- Einträge (Dienst, Benutzername, Passwort) hinzufügen, anzeigen und löschen
- Tresor wird verschlüsselt in einer Datei gespeichert (AES-256-GCM)
- Schlüssel wird aus einem Master-Passwort abgeleitet (PBKDF2, 600.000 Iterationen, zufälliges Salt)
- Falsches Master-Passwort und manipulierte Dateien werden erkannt und abgelehnt
- Passwortgenerator (SecureRandom)
- Master-Passwort ändern
- Unit-Tests mit JUnit 5

## Voraussetzungen

- Java 25 (JDK)
- IntelliJ IDEA (IDE mit Maven-Unterstützung)

## Starten

1. Repository klonen und in der IDE als Maven-Projekt öffnen.
2. `de.mo.vault.Main` ausführen.
3. Beim ersten Start legt das eingegebene Master-Passwort den neuen Tresor fest.
   Die Datei `vault.txt` wird im Projektordner angelegt.

## Tests

In der IDE Rechtsklick auf `src/test/java`, dann **Run 'All Tests'**.

## Menü

```
1) Alle Einträge anzeigen
2) Eintrag hinzufügen
3) Eintrag löschen
4) Master-Passwort ändern
5) Beenden
```