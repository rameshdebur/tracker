package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TrackerIOTest {

    @Test
    public void testOpenFromLibraryNullPaths() {
        assertNull("openFromLibrary should return null for null paths",
            TrackerIO.openFromLibrary(null, null, null));
    }

    @Test
    public void testOpenFromLibraryEmptyPaths() {
        assertNull("openFromLibrary should return null for empty paths",
            TrackerIO.openFromLibrary(new ArrayList<>(), null, null));
    }
}
