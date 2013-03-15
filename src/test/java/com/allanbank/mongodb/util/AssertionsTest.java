/*
 * Copyright 2012-2013, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */

package com.allanbank.mongodb.util;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * AssertionsTest provides test cases for the {@link Assertions} class.
 * 
 * @copyright 2012-2013, Allanbank Consulting, Inc., All Rights Reserved
 */
public class AssertionsTest {

    /**
     * Test method for {@link Assertions#assertNotEmpty(String, String)}.
     */
    @Test
    public void testAssertNotEmptyNotThrowsOnNotNull() {
        try {
            Assertions.assertNotEmpty("f", "Not Expected!");
        }
        catch (final IllegalArgumentException e) {
            fail("Should not have thrown an IllegalArguementException");
        }
    }

    /**
     * Test method for {@link Assertions#assertNotEmpty(String, String)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssertNotEmptyThrowsOnBlank() {
        Assertions.assertNotEmpty(" ", "Expected!");
    }

    /**
     * Test method for {@link Assertions#assertNotEmpty(String, String)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssertNotEmptyThrowsOnNull() {
        Assertions.assertNotEmpty(null, "Expected!");
    }

    /**
     * Test method for {@link Assertions#assertNotNull(Object, String)}.
     */
    @Test
    public void testAssertNotNullNotThrowsOnNotNull() {
        try {
            Assertions.assertNotNull("f", "Not Expected!");
        }
        catch (final IllegalArgumentException e) {
            fail("Should not have thrown an IllegalArguementException");
        }
    }

    /**
     * Test method for {@link Assertions#assertNotNull(Object, String)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssertNotNullThrowsOnNull() {
        Assertions.assertNotNull(null, "Expected!");
    }

}
