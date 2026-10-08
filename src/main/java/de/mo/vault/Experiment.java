package de.mo.vault;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class Experiment {
    public static void main(String[] args) throws GeneralSecurityException {

        byte[] keyBytes = new byte[32];   // 32 Bytes = 256 Bit, hier nur Nullen (Testschlüssel)
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        byte[] encrypted = CryptoService.encrypt("Hallo Welt", key);
        System.out.println(Base64.getEncoder().encodeToString(encrypted));

    }

}
