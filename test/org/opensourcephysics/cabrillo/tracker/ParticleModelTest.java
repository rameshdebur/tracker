package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;
import org.opensourcephysics.tools.Parameter;

public class ParticleModelTest {

    @Test
    public void testNewTimeParam() throws Exception {
        Object result = ParticleModel.newTimeParam();

        assertNotNull("newTimeParam should not return null", result);
        // The object returned should be an instance of Parameter
        assertTrue("newTimeParam should return an instance of Parameter", result instanceof Parameter);

        Parameter param = (Parameter) result;
        assertEquals("Parameter name should be 't'", "t", param.getName());
        assertFalse("Parameter name should not be editable", param.isNameEditable());

        assertNotNull("Parameter description should not be null", param.getDescription());
        assertTrue("Parameter description should not be empty", param.getDescription().length() > 0);

        assertNotNull("Expression should not be null", param.getExpression());
    }
}
