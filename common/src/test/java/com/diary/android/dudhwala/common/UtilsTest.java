package com.diary.android.dudhwala.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsTest {
    private final String TEST_VALID_STRING = "TEST";
    private final String TEST_EMPTY_STRING = "";
    private final String TEST_NULL_STRING = null;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isStringNotEmpty_Valid() {
        assertTrue(Utils.isStringNotEmpty(TEST_VALID_STRING));
    }

    @Test
    public void isStringNotEmpty_Empty() {
        assertFalse(Utils.isStringNotEmpty(TEST_EMPTY_STRING));
    }

    @Test
    public void isStringNotEmpty_Null() {
        assertFalse(Utils.isStringNotEmpty(TEST_NULL_STRING));
    }
}