package de.mo.vault;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordGeneratorTest {

    @Test
    void generatedPasswordHasRequestedLength() {
        String password = PasswordGenerator.generate(16);
        assertEquals(16, password.length());
    }

    @Test
    void generatedPasswordContainsAllCharacterGroups() {
        String password = PasswordGenerator.generate(16);

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        assertTrue(hasLower);
        assertTrue(hasUpper);
        assertTrue(hasDigit);
    }

    @Test
    void tooShortLengthIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> PasswordGenerator.generate(5));
    }
}