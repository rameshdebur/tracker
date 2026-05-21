package org.opensourcephysics.cabrillo.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Map;

public class ExportZipDialogTest {

    private Map<Integer, Object> zipDialogs;
    private TrackerPanel panel;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        // Access the private static zipDialogs map using reflection
        Field zipDialogsField = ExportZipDialog.class.getDeclaredField("zipDialogs");
        zipDialogsField.setAccessible(true);
        // We cast to Map<Integer, Object> to allow inserting a dummy object without Mockito
        zipDialogs = (Map<Integer, Object>) zipDialogsField.get(null);

        // Create a TrackerPanel
        panel = new TrackerPanel(false);
    }

    @After
    public void tearDown() {
        if (panel != null && zipDialogs != null) {
            zipDialogs.remove(panel.getID());
        }
    }

    @Test
    public void testHasDialog() throws Exception {
        // Ensure clear state initially
        zipDialogs.remove(panel.getID());
        assertFalse("Initially should not have a dialog", ExportZipDialog.hasDialog(panel));

        // Use a dummy object to simulate a dialog in the map
        Object dummyDialog = new Object();

        // Add the dummy dialog to the map
        zipDialogs.put(panel.getID(), dummyDialog);

        // Test hasDialog returns true
        assertTrue("Should have dialog after adding to map", ExportZipDialog.hasDialog(panel));

        // Manually remove to simulate clear/close since clear() might crash on partially initialized mock
        zipDialogs.remove(panel.getID());
        assertFalse("Should not have dialog after remove", ExportZipDialog.hasDialog(panel));
    }
}
