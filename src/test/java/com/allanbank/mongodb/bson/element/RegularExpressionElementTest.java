/*
 * Copyright 2012, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */

package com.allanbank.mongodb.bson.element;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.allanbank.mongodb.bson.Element;
import com.allanbank.mongodb.bson.ElementType;
import com.allanbank.mongodb.bson.Visitor;

/**
 * RegularExpressionElementTest provides tests for the
 * {@link RegularExpressionElement} class.
 * 
 * @copyright 2012, Allanbank Consulting, Inc., All Rights Reserved
 */
public class RegularExpressionElementTest {

    /** A sample scope document. */
    public static final String OPTIONS_1 = "i";

    /** A sample scope document. */
    public static final String OPTIONS_2 = "lmisux";

    /**
     * Test method for
     * {@link RegularExpressionElement#accept(com.allanbank.mongodb.bson.Visitor)}
     * .
     */
    @Test
    public void testAccept() {
        final RegularExpressionElement element = new RegularExpressionElement(
                "foo", "func code() {}", OPTIONS_1);

        final Visitor mockVisitor = createMock(Visitor.class);

        mockVisitor.visitRegularExpression(eq("foo"), eq("func code() {}"),
                eq(OPTIONS_1));
        expectLastCall();

        replay(mockVisitor);

        element.accept(mockVisitor);

        verify(mockVisitor);
    }

    /**
     * Test method for {@link RegularExpressionElement#equals(java.lang.Object)}
     * .
     */
    @Test
    public void testEqualsObject() {

        final List<Element> objs1 = new ArrayList<Element>();
        final List<Element> objs2 = new ArrayList<Element>();

        for (final String name : Arrays.asList("1", "foo", "bar", "baz", "2",
                null)) {
            for (final String code : Arrays.asList("1", "foo", "bar", "baz",
                    "2", null)) {
                objs1.add(new RegularExpressionElement(name, code, OPTIONS_1
                        .toLowerCase()));
                objs2.add(new RegularExpressionElement(name, code, OPTIONS_1
                        .toUpperCase()));

                objs1.add(new RegularExpressionElement(name, code, OPTIONS_2
                        .toLowerCase()));
                objs2.add(new RegularExpressionElement(name, code, OPTIONS_2
                        .toUpperCase()));

                objs1.add(new RegularExpressionElement(name, code, 14));
                objs2.add(new RegularExpressionElement(name, code, 14));

                objs1.add(new RegularExpressionElement(name, code, null));
                objs2.add(new RegularExpressionElement(name, code, null));
            }

            objs1.add(new RegularExpressionElement(name, null, 13));
            objs2.add(new RegularExpressionElement(name, null, 13));
        }

        // Sanity check.
        assertEquals(objs1.size(), objs2.size());

        for (int i = 0; i < objs1.size(); ++i) {
            final Element obj1 = objs1.get(i);
            Element obj2 = objs2.get(i);

            assertTrue(obj1.equals(obj1));
            assertNotSame(obj1, obj2);
            assertEquals(obj1, obj2);

            assertEquals(obj1.hashCode(), obj2.hashCode());

            for (int j = i + 1; j < objs1.size(); ++j) {
                obj2 = objs2.get(j);

                assertFalse(obj1.equals(obj2));
                assertFalse(obj1.hashCode() == obj2.hashCode());
            }

            assertFalse(obj1.equals("foo"));
            assertFalse(obj1.equals(null));
            assertFalse(obj1.equals(new MaxKeyElement(obj1.getName())));
        }
    }

    /**
     * Test method for {@link RegularExpressionElement#getPattern()}.
     */
    @Test
    public void testGetPattern() {
        final RegularExpressionElement element = new RegularExpressionElement(
                "foo", "func code() {}", OPTIONS_1);

        assertEquals("func code() {}", element.getPattern());
    }

    /**
     * Test method for {@link RegularExpressionElement#getOptions()}
     */
    @Test
    public void testGetScope() {
        final RegularExpressionElement element = new RegularExpressionElement(
                "foo", "func code() {}", OPTIONS_1);

        assertEquals(RegularExpressionElement.OPTION_I, element.getOptions());
    }

    /**
     * Test method for
     * {@link RegularExpressionElement#RegularExpressionElement(String, String, String)}
     * .
     */
    @Test
    public void testRegularExpressionElement() {
        final RegularExpressionElement element = new RegularExpressionElement(
                "foo", "func code() {}", OPTIONS_1);

        assertEquals("foo", element.getName());
        assertEquals("func code() {}", element.getPattern());
        assertEquals(RegularExpressionElement.OPTION_I, element.getOptions());
        assertEquals(ElementType.REGEX, element.getType());
    }

    /**
     * Test method for
     * {@link RegularExpressionElement#RegularExpressionElement(String, String, String)}
     * .
     */
    @SuppressWarnings("unused")
    @Test
    public void testRegularExpressionElementWithBadOption() {

        final String options = OPTIONS_2.toLowerCase()
                + OPTIONS_2.toUpperCase();
        for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; ++c) {
            if (options.indexOf(c) < 0) {
                try {
                    new RegularExpressionElement("foo", "func code() {}",
                            String.valueOf(c));
                    fail("Should throw an exception on bad option: " + c);
                }
                catch (final IllegalArgumentException good) {
                    // Good.
                }
            }
        }
    }

    /**
     * Test method for {@link RegularExpressionElement#toString()}.
     */
    @Test
    public void testToString() {
        final RegularExpressionElement element = new RegularExpressionElement(
                "foo", "func code() {}", OPTIONS_1);

        assertEquals("\"foo\" : /func code() {}/i", element.toString());
    }
}