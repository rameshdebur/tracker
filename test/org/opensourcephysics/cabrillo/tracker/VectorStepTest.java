package org.opensourcephysics.cabrillo.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VectorStepTest {

    private boolean originalPointSnapEnabled;

    @Before
    public void setUp() {
        // Save the original state before each test
        originalPointSnapEnabled = VectorStep.isPointSnapEnabled();
    }

    @After
    public void tearDown() {
        // Restore the original state after each test
        VectorStep.setPointSnapEnabled(originalPointSnapEnabled);
    }

    @Test
    public void testSetAndGetPointSnapEnabled() {
        // Test setting to true
        VectorStep.setPointSnapEnabled(true);
        assertTrue("Point snap should be enabled", VectorStep.isPointSnapEnabled());

        // Test setting to false
        VectorStep.setPointSnapEnabled(false);
        assertFalse("Point snap should be disabled", VectorStep.isPointSnapEnabled());
    }
}
