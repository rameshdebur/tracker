package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

public class BounceMatrixTest {

    @Test
    public void testIdentitySquare() {
        BounceMatrix matrix = BounceMatrix.identity(3, 3);
        double[][] array = matrix.getArray();

        assertEquals("Number of rows should be 3", 3, array.length);
        assertEquals("Number of columns should be 3", 3, array[0].length);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    assertEquals("Diagonal elements should be 1.0", 1.0, array[i][j], 1e-9);
                } else {
                    assertEquals("Non-diagonal elements should be 0.0", 0.0, array[i][j], 1e-9);
                }
            }
        }
    }

    @Test
    public void testIdentityWideRectangular() {
        BounceMatrix matrix = BounceMatrix.identity(2, 4);
        double[][] array = matrix.getArray();

        assertEquals("Number of rows should be 2", 2, array.length);
        assertEquals("Number of columns should be 4", 4, array[0].length);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    assertEquals("Diagonal elements should be 1.0", 1.0, array[i][j], 1e-9);
                } else {
                    assertEquals("Non-diagonal elements should be 0.0", 0.0, array[i][j], 1e-9);
                }
            }
        }
    }

    @Test
    public void testIdentityTallRectangular() {
        BounceMatrix matrix = BounceMatrix.identity(4, 2);
        double[][] array = matrix.getArray();

        assertEquals("Number of rows should be 4", 4, array.length);
        assertEquals("Number of columns should be 2", 2, array[0].length);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == j) {
                    assertEquals("Diagonal elements should be 1.0", 1.0, array[i][j], 1e-9);
                } else {
                    assertEquals("Non-diagonal elements should be 0.0", 0.0, array[i][j], 1e-9);
                }
            }
        }
    }

    @Test
    public void testIdentityZero() {
        BounceMatrix matrix = BounceMatrix.identity(0, 0);
        double[][] array = matrix.getArray();

        assertEquals("Number of rows should be 0", 0, array.length);
    }

    @Test
    public void testIdentityOneZero() {
        BounceMatrix matrix = BounceMatrix.identity(1, 0);
        double[][] array = matrix.getArray();

        assertEquals("Number of rows should be 1", 1, array.length);
        assertEquals("Number of columns should be 0", 0, array[0].length);
    }

    @Test
    public void testIdentityZeroOne() {
        BounceMatrix matrix = BounceMatrix.identity(0, 1);
        double[][] array = matrix.getArray();

        assertEquals("Number of rows should be 0", 0, array.length);
    }
}
