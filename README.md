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

## Bedrohungsmodell

### Wovor der Tresor schützt

- **Diebstahl der Datei:** Wer nur `vault.txt` in die Hände bekommt, sieht
  verschlüsselte Daten (AES-256-GCM). Ohne Master-Passwort sind sie nicht lesbar.
- **Raten des Master-Passworts:** PBKDF2 mit 600.000 Iterationen und zufälligem
  Salt verhindert jeden Rateversuch und macht fertige Passwort-Tabellen nutzlos.
- **Veränderung der Datei:** GCM erkennt manipulierte Daten, das Programm
  lehnt sie mit einer Fehlermeldung ab.

### Wovor er NICHT schützt

- **Schwaches Master-Passwort:** Ist es kurz oder erratbar, hilft auch PBKDF2 wenig.
- **Schadsoftware auf dem Rechner:** Keylogger oder Speicherauslesen sehen das
  Passwort, sobald der Tresor geöffnet ist.
- **Sichtbare Eingabe:** Das Master-Passwort wird beim Tippen in der Konsole angezeigt.
- **Passwörter im Arbeitsspeicher:** Einträge liegen als Java-Strings im Speicher
  und lassen sich nicht zuverlässig löschen.
- **Löschen oder Zurückspielen der Datei:** Ein Angreifer kann die Datei löschen
  oder durch eine ältere Version ersetzen. GCM erkennt das nicht.
- **Metadaten:** Dateigröße und Änderungszeitpunkt sind sichtbar.
- **Kein Ersatz für geprüfte Software:** Das Programm wurde NICHT extern geprüft.

