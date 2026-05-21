package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

public class VectorStepTest {

    @Test
    public void testIsVectorSnapEnabled() {
        // Initial state or default state check. Usually it is true based on source code.
        boolean initialState = VectorStep.isVectorSnapEnabled();

        // Disable it and check
        VectorStep.setVectorSnapEnabled(false);
        assertFalse("Vector snap should be disabled", VectorStep.isVectorSnapEnabled());

        // Enable it and check
        VectorStep.setVectorSnapEnabled(true);
        assertTrue("Vector snap should be enabled", VectorStep.isVectorSnapEnabled());

        // Restore initial state to not affect other tests
        VectorStep.setVectorSnapEnabled(initialState);
    }
}
