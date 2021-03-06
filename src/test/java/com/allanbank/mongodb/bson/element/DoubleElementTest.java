/*
 * #%L
 * DoubleElementTest.java - mongodb-async-driver - Allanbank Consulting, Inc.
 * %%
 * Copyright (C) 2011 - 2014 Allanbank Consulting, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.allanbank.mongodb.bson.element;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.allanbank.mongodb.bson.Element;
import com.allanbank.mongodb.bson.ElementType;
import com.allanbank.mongodb.bson.Visitor;

/**
 * DoubleElementTest provides tests for the {@link DoubleElement} class.
 *
 * @copyright 2012-2013, Allanbank Consulting, Inc., All Rights Reserved
 */
public class DoubleElementTest {

    /**
     * Test method for
     * {@link DoubleElement#accept(com.allanbank.mongodb.bson.Visitor)} .
     */
    @Test
    public void testAccept() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        final Visitor mockVisitor = createMock(Visitor.class);

        mockVisitor.visitDouble(eq("foo"), eq(1.0101));
        expectLastCall();

        replay(mockVisitor);

        element.accept(mockVisitor);

        verify(mockVisitor);
    }

    /**
     * Test method for {@link DoubleElement#compareTo(Element)}.
     */
    @Test
    public void testCompareTo() {
        final DoubleElement a1 = new DoubleElement("a", 1.0);
        final DoubleElement a11 = new DoubleElement("a", 11.0);
        final DoubleElement b1 = new DoubleElement("b", 1.0);

        final DoubleElement a2 = new DoubleElement("a", 2.1);
        final DoubleElement a3 = new DoubleElement("a", 1.9);

        final IntegerElement i = new IntegerElement("a", 2);
        final LongElement l = new LongElement("a", 2);

        final Element other = new MaxKeyElement("a");

        assertEquals(0, a1.compareTo(a1));

        assertTrue(a1.compareTo(a11) < 0);
        assertTrue(a11.compareTo(a1) > 0);

        assertTrue(a1.compareTo(b1) < 0);
        assertTrue(b1.compareTo(a1) > 0);

        assertTrue(a1.compareTo(i) < 0);
        assertTrue(i.compareTo(a1) > 0);

        assertTrue(a1.compareTo(l) < 0);
        assertTrue(l.compareTo(a1) > 0);

        assertTrue(a3.compareTo(i) < 0);
        assertTrue(i.compareTo(a3) > 0);

        assertTrue(a3.compareTo(l) < 0);
        assertTrue(l.compareTo(a3) > 0);

        assertTrue(a2.compareTo(i) > 0);
        assertTrue(i.compareTo(a2) < 0);

        assertTrue(a2.compareTo(l) > 0);
        assertTrue(l.compareTo(a2) < 0);

        assertTrue(a1.compareTo(other) < 0);
        assertTrue(other.compareTo(a1) > 0);
    }

    /**
     * Test method for
     * {@link DoubleElement#DoubleElement(java.lang.String, double)} .
     */
    @Test
    public void testDoubleElement() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals("foo", element.getName());
        assertEquals(1.0101, element.getValue(), 0.0001);
        assertEquals(ElementType.DOUBLE, element.getType());
    }

    /**
     * Test method for {@link DoubleElement#equals(java.lang.Object)} .
     */
    @Test
    public void testEqualsObject() {

        final List<Element> objs1 = new ArrayList<Element>();
        final List<Element> objs2 = new ArrayList<Element>();

        for (final String name : Arrays.asList("1", "foo", "bar", "baz", "2")) {
            for (int i = 0; i < 10; ++i) {
                final double value = Math.random();
                objs1.add(new DoubleElement(name, value));
                objs2.add(new DoubleElement(name, value));
            }
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
     * Test method for {@link DoubleElement#getDoubleValue()} .
     */
    @Test
    public void testGetDoubleValue() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals(1.0101, element.getDoubleValue(), 0.0001);
    }

    /**
     * Test method for {@link DoubleElement#getIntValue()}.
     */
    @Test
    public void testGetIntValue() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals(1, element.getIntValue());
    }

    /**
     * Test method for {@link DoubleElement#getLongValue()}.
     */
    @Test
    public void testGetLongValue() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals(1L, element.getLongValue());
    }

    /**
     * Test method for {@link DoubleElement#getValue()}.
     */
    @Test
    public void testGetValue() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals(1.0101, element.getValue(), 0.0001);
    }

    /**
     * Test method for {@link DoubleElement#DoubleElement}.
     */
    @SuppressWarnings("unused")
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnNullName() {

        new DoubleElement(null, 1);
    }

    /**
     * Test method for {@link DoubleElement#toString()}.
     */
    @Test
    public void testToString() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals("foo : 1.0101", element.toString());
    }

    /**
     * Test method for {@link DoubleElement#getValueAsObject()}.
     */
    @Test
    public void testValueAsObject() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals(Double.valueOf(element.getDoubleValue()),
                element.getValueAsObject());
    }

    /**
     * Test method for {@link DoubleElement#getValueAsString()}.
     */
    @Test
    public void testValueAsString() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertEquals("1.0101", element.getValueAsString());
    }

    /**
     * Test method for {@link DoubleElement#withName(String)}.
     */
    @Test
    public void testWithName() {
        DoubleElement element = new DoubleElement("foo", 1.0101);

        element = element.withName("bar");
        assertEquals("bar", element.getName());
        assertEquals(1.0101, element.getValue(), 0.0001);
        assertEquals(ElementType.DOUBLE, element.getType());
    }

    /**
     * Test method for {@link DoubleElement#withName(String)}.
     */
    @Test
    public void testWithNameWhenSameName() {
        final DoubleElement element = new DoubleElement("foo", 1.0101);

        assertSame(element, element.withName("foo"));
    }
}
