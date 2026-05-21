package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.BitSet;

import org.opensourcephysics.media.core.Video;

public class TrackerIOTest {

    @BeforeClass
    public static void setUp() {
        try {
            java.lang.reflect.Field isJSField = org.opensourcephysics.display.OSPRuntime.class.getField("isJS");
            if (isJSField != null) {
                 isJSField.setBoolean(null, false);
            }
        } catch (Error e) {
        } catch (Exception e) {
        }
    }

    @Test
    public void testFindBadVideoFramesNullVideo() {
        TrackerPanel panel = mock(TrackerPanel.class);
        when(panel.getVideo()).thenReturn(null);

        BitSet result = TrackerIO.findBadVideoFrames(panel, 0.1, false, false, false);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindBadVideoFramesNoOutliers() {
        TrackerPanel panel = mock(TrackerPanel.class);
        Video video = mock(Video.class);
        when(panel.getVideo()).thenReturn(video);
        when(video.getOutliers(0.1)).thenReturn(new BitSet());

        BitSet result = TrackerIO.findBadVideoFrames(panel, 0.1, false, false, false);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindBadVideoFramesWithOutliersNoDialog() {
        TrackerPanel panel = mock(TrackerPanel.class);
        Video video = mock(Video.class);
        when(panel.getVideo()).thenReturn(video);

        BitSet outliers = new BitSet();
        outliers.set(1);
        outliers.set(5);
        when(video.getOutliers(0.1)).thenReturn(outliers);

        BitSet result = TrackerIO.findBadVideoFrames(panel, 0.1, false, false, false);

        assertNotNull(result);
        assertEquals(2, result.cardinality());
        assertTrue(result.get(1));
        assertTrue(result.get(5));
    }
}
