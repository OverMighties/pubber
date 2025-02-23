package com.overmighties.pubber.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

public class TestDateType {
    @Test
    public void testNone(){
        DateType none = DateType.NONE;
        assertEquals(none.getTime(), "");
        assertEquals(none.isType(), null);
    }
}
