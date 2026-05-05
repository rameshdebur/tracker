package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;
import org.opensourcephysics.media.core.TPoint;

public class TRegionTest {

    // Dummy subclass to bypass the image constructor and easily add points
    private static class DummyTRegion extends TRegion {
        public DummyTRegion() {
            super(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB), 0, 0);
            this.reset(); // clear any points added by findEdge during initialization
        }
    }

    @Test
    public void testGetCenterZeroPoints() {
        DummyTRegion region = new DummyTRegion();
        TPoint center = region.getCenter();
        assertNull("Center should be null when there are no points", center);
    }

    @Test
    public void testGetCenterOnePoint() {
        DummyTRegion region = new DummyTRegion();
        region.addPoint(10, 20);
        TPoint center = region.getCenter();
        assertNotNull("Center should not be null", center);
        assertEquals(10.0, center.getX(), 0.001);
        assertEquals(20.0, center.getY(), 0.001);
    }

    @Test
    public void testGetCenterMultiplePoints() {
        DummyTRegion region = new DummyTRegion();
        region.addPoint(0, 0);
        region.addPoint(10, 0);
        region.addPoint(10, 10);
        region.addPoint(0, 10);
        TPoint center = region.getCenter();
        assertNotNull("Center should not be null", center);
        // The center of this polygon is computed via getBounds2D().getCenterX() and getCenterY()
        // which gives the center of the bounding box. For these 4 points, the bounding box
        // is x=0 to 10, y=0 to 10. The center is (5.0, 5.0).
        assertEquals(5.0, center.getX(), 0.001);
        assertEquals(5.0, center.getY(), 0.001);
    }
}
