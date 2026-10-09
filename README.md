# Password Vault
![Build](https://github.com/m3xXmo0/password-vault/actions/workflows/build.yml/badge.svg)
## Deutsche Version:

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

## Dateiformat

Die Datei enthält Base64 von: Salt (16 Bytes) | Nonce (12 Bytes) | Chiffretext + GCM-Tag (16 Bytes).
Das Salt wird bei jedem Speichern neu erzeugt, ebenso die Nonce.

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
  Salt erschwert jeden Rateversuch und macht fertige Passwort-Tabellen nutzlos.
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
- **Kein Ersatz für geprüfte Software:** Das Programm wurde **NICHT** extern geprüft.

## Hinweis: Lernprojekt

Dieses Programm ist ein Lernprojekt und **nicht für echte Passwörter gedacht**.
Es wurde nicht extern geprüft und hat bekannte Einschränkungen (siehe
Bedrohungsmodell). Für echte Passwörter bitte einen etablierten Passwort-Manager
verwenden.

## Was ich gelernt habe

Die Anwendung der Krypto-Klassen war schnell erledigt. Schwerer war es, zu verstehen,
**warum** man sie so benutzen muss:

- **Nonce bei AES-GCM:** Mit demselben Schlüssel darf eine Nonce nie zweimal
  vorkommen, sonst bricht die Sicherheit zusammen. Deshalb wird bei jedem
  Speichern eine neue erzeugt. Die mathematischen Details von GCM kenne ich nicht,
  ich weiß aber, was Nonce, Tag und Schlüssel bewirken und was bei falscher
  Benutzung passiert.
- **Salt und Iterationen bei PBKDF2:** Ein Angreifer rät das Passwort, nicht den
  Schlüssel. Die Iterationen machen jeden Rateversuch teuer, das Salt macht
  vorberechnete Passwort-Tabellen nutzlos.
- **Kapselung:** `private` reicht nicht, wenn eine Methode die interne Liste
  herausgibt. Deshalb liefert `getEntries()` eine unveränderbare Kopie.
- **Fehlerbehandlung:** Falsches Passwort, beschädigte Datei und Manipulation
  werden unterschieden und als verständliche Meldung ausgegeben.
- **Bedrohungsmodell:** Ehrlich benennen, wovor das Programm nicht schützt.

## Nutzung von KI

Ich habe dieses Projekt mit Unterstützung von Claude als Lernbegleiter umgesetzt.  
Die KI hat mir Konzepte erklärt (AES-GCM, PBKDF2, Salt, Nonce, Maven, JUnit) und mich in
kleinen Schritten durch den Aufbau geführt.
Der Code wurde von mir eigenständig geschrieben, 
angepasst und ausgeführt. Compiler- und Laufzeitfehler selbst gesucht / behoben und anschließend den Code in kleinen Schritten committed.

Bei der kryptografischen Theorie hinter AES-GCM und PBKDF2 war ich stark auf
die KI angewiesen, weil mir die mathematischen Grundlagen dafür noch fehlen.
Ich kann erklären, was die Bausteine bewirken und wie man sie richtig
einsetzt, die mathematischen Details beherrsche ich aber **NOCH** nicht.

.. / .... --- .--. . / -.-- --- ..- / .-.. .. -.- . / -- -.-- / ..-. .. .-. ... - / .--. .-. --- .--- . -.-. -
## English version:
# Password Vault

A command-line password vault written purely in Java.  
Educational project: Designed to understand how symmetric encryption, key derivation
and integrity verification work together in practice.

## Features

- Add, view, and delete entries (service, username, password)
- Encrypted file storage using AES-256-GCM
- Key derivation from a master password via PBKDF2 (600,000 iterations, random salt)
- Authentication and integrity verification: Incorrect master passwords and manipulated files are detected and rejected
- Password generator powered by SecureRandom
- Master password update functionality
- Unit tests with JUnit 5

## Prerequisites

- Java 25 (JDK)
- IntelliJ IDEA (or any IDE with Maven support)

## File Format

The file contains the Base64 representation of:  
Salt (16 bytes) | Nonce (12 bytes) | Ciphertext + GCM Tag (16 bytes).

Both the salt and the nonce are regenerated on every save operation.

## Getting Started

1. Clone the repository and open it as a Maven project in your IDE.
2. Run `de.mo.vault.Main`.
3. On first startup, the master password you enter will set up the new vault. 
The file `vault.txt` will be created in the project root directory.

## Tests

In your IDE, right-click on `src/test/java` and select **Run 'All Tests'**.

## Menu
    1) View all entries
    2) Add entry
    3) Delete entry
    4) Change master password
    5) Exit

## Threat Model

### What the vault protects against

- **File Theft:** Anyone who gets hold of `vault.txt` only sees encrypted data (AES-256-GCM). Without the master password, it cannot be read.
- **Master Password Guessing:** PBKDF2 with 600,000 iterations and a random salt makes any guessing attempt expensive and makes precomputed password tables useless.
- **File Manipulation:** GCM detects tampered data; the application rejects it with an error message.

### What it does NOT protect against

- **Weak Master Password:** If it is short or predictable, PBKDF2 provides little help.
- **Malware on the Machine:** Keyloggers or memory dumping tools see the password as soon as the vault is opened.
- **Visible Input:** The master password is displayed in plain text when typed into the console.
- **Passwords in RAM:** Entries are stored as Java Strings in memory and cannot be reliably wiped.
- **File Deletion or Rollback:** An attacker can delete the file or replace it with an older version. GCM cannot detect this.
- **Metadata:** File size and modification timestamps are visible.
- **No Replacement for Audited Software:** The program has **NOT** been externally audited.


## Note: Educational Project

This program is a learning project and **not intended for real passwords**. 
It has not been externally audited and has known limitations (see Threat Model). 
For actual passwords, please use an established password manager.

## What I Learned

Applying the crypto classes was done quickly. The harder part was understanding **why** they must be used that way:

- **Nonce in AES-GCM:** A nonce must never be used twice with the same key, otherwise security completely breaks down. Therefore, a new one is generated with every save operation. I do not know the mathematical details of GCM, but I know what nonce, tag, and key do, and what happens if they are used incorrectly.
- **Salt and Iterations in PBKDF2:** An attacker guesses the password, not the key. The iterations make every guessing attempt expensive, and the salt makes precomputed password tables useless.
- **Encapsulation:** `private` is not enough if a method exposes the internal list. Therefore, `getEntries()` returns an unmodifiable copy.
- **Error Handling:** Distinguishing between incorrect password, corrupted file, and tampering, presenting them as clear error messages.
- **Threat Model:** Honestly naming what the program does not protect against.

## Use of AI

I developed this project with the support of Claude as a learning companion.  
The AI explained concepts to me (AES-GCM, PBKDF2, Salt, Nonce) and guided me step by step through the project setup.  
The code was written, adapted, and executed independently by me. I searched for and fixed compiler and runtime errors myself and subsequently committed the code in small increments.

When it came to the cryptographic theory behind AES-GCM and PBKDF2, I relied heavily on the AI because I still lack the mathematical foundation for it.  
I can explain what the components do and how to use them correctly, but I do **NOT YET** command the mathematical details.