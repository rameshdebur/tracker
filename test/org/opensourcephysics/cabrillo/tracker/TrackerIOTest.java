package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.File;

public class TrackerIOTest {

    @Test
    public void testOpenURL() throws Exception {
        AtomicBoolean whenDoneRan = new AtomicBoolean(false);
        Runnable whenDone = () -> whenDoneRan.set(true);

        TrackerIO.AsyncLoader loader = TrackerIO.openURL("http://example.com/test.trk", null, whenDone);
        assertNotNull("AsyncLoader should not be null", loader);

        // Wait up to 1 second for AsyncSwingWorker to complete
        for (int i = 0; i < 100; i++) {
            if (whenDoneRan.get()) break;
            Thread.sleep(10);
        }

        assertTrue("whenDone runnable should have been executed", whenDoneRan.get());
    }

    @Test
    public void testOpenURLWithNullRunnable() {
        TrackerIO.AsyncLoader loader = TrackerIO.openURL("http://example.com/test.trk", null, null);
        assertNotNull("AsyncLoader should not be null even when runnable is null", loader);
    }

    @Test
    public void testOpenURLLocalFile() throws Exception {
        // Test with a local file URL
        AtomicBoolean whenDoneRan = new AtomicBoolean(false);
        Runnable whenDone = () -> whenDoneRan.set(true);

        File tempFile = File.createTempFile("test", ".trk");
        tempFile.deleteOnExit();

        TrackerIO.AsyncLoader loader = TrackerIO.openURL(tempFile.getAbsolutePath(), null, whenDone);
        assertNotNull("AsyncLoader should not be null for local file", loader);

        for (int i = 0; i < 100; i++) {
            if (whenDoneRan.get()) break;
            Thread.sleep(10);
        }

        assertTrue("whenDone runnable should have been executed", whenDoneRan.get());
    }
}
