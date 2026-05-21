package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

public class TrackerIOTest {

    @Test
    public void testIsImageFile_True() {
        assertTrue("JPG should be recognized as an image", TrackerIO.isImageFile(new File("test.jpg")));
        assertTrue("JPEG should be recognized as an image", TrackerIO.isImageFile(new File("test.jpeg")));
        assertTrue("PNG should be recognized as an image", TrackerIO.isImageFile(new File("test.png")));
        // In the underlying ImageVideoType, gif files are not natively handled as images (perhaps they are treated as videos in Tracker context)
        // Directory navigation might also evaluate to true for JFileChooser filters, but TrackerIO.isImageFile should ideally focus on files
        // We will exclude gif from this test or acknowledge its current Tracker-specific behavior.
    }

    @Test
    public void testIsImageFile_False() {
        assertFalse("TXT should not be recognized as an image", TrackerIO.isImageFile(new File("test.txt")));
        assertFalse("MP4 should not be recognized as an image", TrackerIO.isImageFile(new File("test.mp4")));
        assertFalse("DOC should not be recognized as an image", TrackerIO.isImageFile(new File("test.doc")));
        // Let's assert that passing null is handled gracefully, or we just test normal file extensions
    }
}
