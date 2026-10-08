package de.mo.vault;

import org.junit.jupiter.api.Test;

import javax.crypto.AEADBadTagException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CryptoServiceTest {

    // Baut einen Testschlüssel, bei dem die 32 Bytes alle den gleichen Wert haben
    private static SecretKey keyWithValue(int value) {
        byte[] bytes = new byte[32];
        Arrays.fill(bytes, (byte) value);
        return new SecretKeySpec(bytes, "AES");
    }

    @Test
    void decryptReturnsOriginalText() throws Exception {
        SecretKey key = keyWithValue(1);
        byte[] encrypted = CryptoService.encrypt("Hallo Welt äöü", key);
        assertEquals("Hallo Welt äöü", CryptoService.decrypt(encrypted, key));
    }

    @Test
    void encryptingTwiceGivesDifferentResults() throws Exception {
        SecretKey key = keyWithValue(1);
        byte[] first = CryptoService.encrypt("gleicher Text", key);
        byte[] second = CryptoService.encrypt("gleicher Text", key);
        assertFalse(Arrays.equals(first, second));
    }

    @Test
    void wrongKeyIsRejected() throws Exception {
        byte[] encrypted = CryptoService.encrypt("geheim", keyWithValue(1));
        assertThrows(AEADBadTagException.class,
                () -> CryptoService.decrypt(encrypted, keyWithValue(2)));
    }

    @Test
    void manipulatedDataIsDetected() throws Exception {
        SecretKey key = keyWithValue(1);
        byte[] encrypted = CryptoService.encrypt("geheim", key);
        encrypted[encrypted.length - 1] ^= 1;   // kippt ein einzelnes Bit im letzten Byte
        assertThrows(AEADBadTagException.class,
                () -> CryptoService.decrypt(encrypted, key));
    }
}