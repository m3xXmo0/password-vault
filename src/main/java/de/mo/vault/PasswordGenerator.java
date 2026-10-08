package de.mo.vault;

import java.security.SecureRandom;

/**
 * Erzeugt zufällige Passwörter aus Buchstaben, Ziffern und Sonderzeichen.
 * Benutzt SecureRandom, weil normales Random vorhersehbar (berechenbar) wäre.
 */
public class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+?";
    private static final String ALL = LOWER + UPPER + DIGITS + SYMBOLS;

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Erzeugt ein Passwort der gewünschten Länge (mindestens 8).
     * Es enthält je ein Zeichen aus jeder Gruppe.
     */
    public static String generate(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Länge muss mindestens 8 sein");
        }
        char[] result = new char[length];
        result[0] = pick(LOWER);
        result[1] = pick(UPPER);
        result[2] = pick(DIGITS);
        result[3] = pick(SYMBOLS);
        for (int i = 4; i < length; i++) {
            result[i] = pick(ALL);
        }
        // Mischen (Fisher-Yates), damit die ersten vier Stellen nicht vorhersehbar sind
        for (int i = length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char tmp = result[i];
            result[i] = result[j];
            result[j] = tmp;
        }
        return new String(result);
    }

    private static char pick(String chars) {
        return chars.charAt(RANDOM.nextInt(chars.length()));
    }
}
