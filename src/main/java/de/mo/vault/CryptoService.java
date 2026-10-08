package de.mo.vault;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;


/**
 * Verschlüsselt und entschlüsselt Text mit AES-256-GCM.
 * Der Schlüssel wird in dieser Version noch fest vorgegeben (nur zum Lernen).
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



}
