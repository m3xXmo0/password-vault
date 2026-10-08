package de.mo.vault;

public class VaultException extends Exception {
    /**
     * Fehler beim Öffnen des Tresors z.B. falsches Master-Passwort
     * oder beschädigte Datei. Meldung ist für den Nutzer lesbar.
     */
        public VaultException(String message) {
            super(message);

        }
}
