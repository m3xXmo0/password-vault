package de.mo.vault;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EntryTest {

    @Test
    void toStringShowsServiceAndUsername() {
        Entry entry = new Entry("Netflix", "mo", "geheim123");
        assertEquals("Netflix (mo)", entry.toString());
    }

    @Test
    void toStringDoesNotContainPassword() {
        Entry entry = new Entry("Netflix", "mo", "geheim123");
        assertFalse(entry.toString().contains("geheim123"));
    }
}