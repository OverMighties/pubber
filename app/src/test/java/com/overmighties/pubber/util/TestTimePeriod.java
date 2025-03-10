package com.overmighties.pubber.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestTimePeriod {
    @Test
    public void testNone(){
        TimePeriod none = TimePeriod.NONE;
        assertEquals(none.getTime(), "");
        assertNull(none.isType());
    }
}
