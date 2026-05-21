package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;
import org.opensourcephysics.display.TeXParser;

public class TrackViewTest {

    @Test
    public void testTrimDefined() {
        // Test null input
        assertNull(TrackView.trimDefined(null));

        // Test normal input without defined as
        assertEquals("normal", TrackView.trimDefined("normal"));

        // Test input with defined as
        assertEquals("defined", TrackView.trimDefined("defined" + ": " + "something"));

        // Test subscript removal
        assertEquals("name", TrackView.trimDefined("name_0"));

        // Test subscript removal and defined as
        assertEquals("name", TrackView.trimDefined("name_0" + ": " + "something"));
    }
}
