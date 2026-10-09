package de.mo.vault;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

    class VaultTest {

        @Test
        void addNullIsRejected() {
            Vault vault = new Vault();
            assertThrows(IllegalArgumentException.class, () -> vault.add(null));

    }

        @Test
        void findByServiceIgnoresCase() {
            Vault vault = new Vault();
            vault.add(new Entry("GitHub", "mo", "pw"));
            assertNotNull(vault.findByService("github"));
        }

        @Test
        void duplicateServiceIsRejected() {
            Vault vault = new Vault();
            vault.add(new Entry("GitHub", "mo", "pw"));
            assertThrows(IllegalArgumentException.class,
                    () -> vault.add(new Entry("github", "x", "y")));
        }
}