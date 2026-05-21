package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackerIOTest {

    private TFrame mockFrame;
    private TrackerPanel mockPanel;

    @Before
    public void setUp() {
        mockFrame = mock(TFrame.class);
        mockPanel = mock(TrackerPanel.class);
    }

    @Test
    public void testLoadFiles_emptyList() {
        List<File> fileList = new ArrayList<>();
        boolean result = TrackerIO.loadFiles(mockFrame, fileList, mockPanel);
        assertTrue("loadFiles should return true for empty list", result);
        verify(mockFrame).setCursor(java.awt.Cursor.getDefaultCursor());
        verifyNoInteractions(mockPanel);
    }

    @Test
    public void testLoadFiles_nullList() {
        // null fileList causes NullPointerException at nf = fileList.size()
        // caught by catch (Exception e) and returns false
        boolean result = TrackerIO.loadFiles(mockFrame, null, mockPanel);
        assertFalse("loadFiles should return false for null list", result);
    }

    @Test
    public void testLoadFiles_withDataFile() throws IOException {
        List<File> fileList = new ArrayList<>();
        File dataFile = File.createTempFile("testdata", ".txt");
        dataFile.deleteOnExit();
        fileList.add(dataFile);

        boolean result = TrackerIO.loadFiles(mockFrame, fileList, mockPanel);

        assertTrue("loadFiles should return true when processing data file", result);
        // haveOneData = true, so targetPanel.importDataAsync is called
        verify(mockPanel).importDataAsync(org.opensourcephysics.controls.XML.getAbsolutePath(dataFile), null, null);
    }

    @Test
    public void testLoadFiles_withoutTargetPanel() throws IOException {
        List<File> fileList = new ArrayList<>();
        File dataFile = File.createTempFile("testdata", ".txt");
        dataFile.deleteOnExit();
        fileList.add(dataFile);

        // pass null as targetPanel
        boolean result = TrackerIO.loadFiles(mockFrame, fileList, null);

        assertTrue("loadFiles should return true when processing data file without target panel", result);
        // since targetPanel is null, list.add(XML.getAbsolutePath(file)) should be called
        // and openFiles should be called in finally block.
        // openFiles is static, we can't easily verify it with basic Mockito, but we can verify it doesn't crash.
    }
}
