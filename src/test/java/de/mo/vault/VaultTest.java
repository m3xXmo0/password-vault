package de.mo.vault;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

    class VaultTest {

        @Test
        void addNullIsRejected() {
            Vault vault = new Vault();
            assertThrows(IllegalArgumentException.class, () -> vault.add(null));

    }
}