package de.mo.vault;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;


/**
 * Verschlüsselt und entschlüsselt Text mit AES-256-GCM
 * Der Schlüssel wird in dieser Version noch fest vorgegeben (nur zum Lernen)
 */


public class CryptoService {

    private static final int NONCE_LENGTH = 12;      // Bytes
    private static final int TAG_LENGTH_BITS = 128;  // Länge der Manipulationsprüfung

    public static byte[] encrypt(String plaintext, SecretKey key) throws GeneralSecurityException{

        byte[] nonce = new byte[NONCE_LENGTH];
        new SecureRandom().nextBytes(nonce);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, nonce));
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        byte[] result = new byte[nonce.length + ciphertext.length];
        System.arraycopy(nonce, 0, result, 0, nonce.length);
        System.arraycopy(ciphertext, 0, result, nonce.length, ciphertext.length);
        return result;

    }

    public static String decrypt(byte[] data, SecretKey key) throws GeneralSecurityException {
        byte[] nonce = new byte[NONCE_LENGTH];
        System.arraycopy(data, 0, nonce, 0, NONCE_LENGTH);

        byte[] ciphertext = new byte[data.length - NONCE_LENGTH];
        System.arraycopy(data, NONCE_LENGTH, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, nonce));
        byte[] plaintext = cipher.doFinal(ciphertext);

        return new String(plaintext, StandardCharsets.UTF_8);
    }
    public static final int SALT_LENGTH = 16;        // Bytes
    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH_BITS = 256;

    /**
     * Leitet aus Master-Passwort und Salt einen AES-Schlüssel ab (PBKDF2)
     * Gleiches Passwort + gleiches Salt ergibt immer denselben Schlüssel
     */
    public static SecretKey deriveKey(char[] password, byte[] salt) throws GeneralSecurityException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH_BITS);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();
            return new SecretKeySpec(keyBytes, "AES");
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Erzeugt ein neues zufälliges Salt
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }



}
