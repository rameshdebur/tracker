package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PencilDrawerTest {

    @Mock
    private TrackerPanel mockPanel;

    @Mock
    private TFrame mockFrame;

    @Mock
    private PencilControl mockDrawingControl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockPanel.getID()).thenReturn(1);
        when(mockPanel.getTFrame()).thenReturn(mockFrame);
    }

    @Test
    public void testIsDrawing_DrawingControlIsNull() {
        // By default, a new PencilDrawer has no drawingControl
        assertFalse("Should not be drawing if drawingControl is null", PencilDrawer.isDrawing(mockPanel));
    }

    @Test
    public void testIsDrawing_DrawingControlIsNotNullButNotVisible() {
        PencilDrawer drawer = PencilDrawer.getDrawer(mockPanel);
        drawer.drawingControl = mockDrawingControl;
        when(mockDrawingControl.isVisible()).thenReturn(false);

        assertFalse("Should not be drawing if drawingControl is not visible", PencilDrawer.isDrawing(mockPanel));
    }

    @Test
    public void testIsDrawing_DrawingControlIsNotNullAndVisible() {
        PencilDrawer drawer = PencilDrawer.getDrawer(mockPanel);
        drawer.drawingControl = mockDrawingControl;
        when(mockDrawingControl.isVisible()).thenReturn(true);

        assertTrue("Should be drawing if drawingControl is visible", PencilDrawer.isDrawing(mockPanel));
    }

    @After
    public void tearDown() {
        PencilDrawer.dispose(mockPanel);
    }
}
