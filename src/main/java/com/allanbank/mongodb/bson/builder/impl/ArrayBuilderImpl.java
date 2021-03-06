/*
 * #%L
 * ArrayBuilderImpl.java - mongodb-async-driver - Allanbank Consulting, Inc.
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
package com.allanbank.mongodb.bson.builder.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.concurrent.NotThreadSafe;

import com.allanbank.mongodb.bson.DocumentAssignable;
import com.allanbank.mongodb.bson.Element;
import com.allanbank.mongodb.bson.ElementAssignable;
import com.allanbank.mongodb.bson.builder.ArrayBuilder;
import com.allanbank.mongodb.bson.builder.BuilderFactory;
import com.allanbank.mongodb.bson.builder.DocumentBuilder;
import com.allanbank.mongodb.bson.element.ArrayElement;
import com.allanbank.mongodb.bson.element.BinaryElement;
import com.allanbank.mongodb.bson.element.BooleanElement;
import com.allanbank.mongodb.bson.element.DocumentElement;
import com.allanbank.mongodb.bson.element.DoubleElement;
import com.allanbank.mongodb.bson.element.IntegerElement;
import com.allanbank.mongodb.bson.element.JavaScriptElement;
import com.allanbank.mongodb.bson.element.JavaScriptWithScopeElement;
import com.allanbank.mongodb.bson.element.JsonSerializationVisitor;
import com.allanbank.mongodb.bson.element.LongElement;
import com.allanbank.mongodb.bson.element.MaxKeyElement;
import com.allanbank.mongodb.bson.element.MinKeyElement;
import com.allanbank.mongodb.bson.element.MongoTimestampElement;
import com.allanbank.mongodb.bson.element.NullElement;
import com.allanbank.mongodb.bson.element.ObjectId;
import com.allanbank.mongodb.bson.element.ObjectIdElement;
import com.allanbank.mongodb.bson.element.RegularExpressionElement;
import com.allanbank.mongodb.bson.element.StringElement;
import com.allanbank.mongodb.bson.element.SymbolElement;
import com.allanbank.mongodb.bson.element.TimestampElement;
import com.allanbank.mongodb.bson.element.UuidElement;

/**
 * A builder for BSON arrays.
 *
 * @api.no This class is <b>NOT</b> part of the drivers API. This class may be
 *         mutated in incompatible ways between any two releases of the driver.
 * @copyright 2011-2013, Allanbank Consulting, Inc., All Rights Reserved
 */
@NotThreadSafe
public class ArrayBuilderImpl
        extends AbstractBuilder
        implements ArrayBuilder {

    /**
     * Creates a new {@link ArrayBuilderImpl}.
     */
    public ArrayBuilderImpl() {
        this(null);
    }

    /**
     * Creates a new {@link ArrayBuilderImpl}.
     *
     * @param outerBuilder
     *            The outer builder scope.
     */
    public ArrayBuilderImpl(final AbstractBuilder outerBuilder) {
        super(outerBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final boolean value) {
        return addBoolean(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final byte subType, final byte[] data)
            throws IllegalArgumentException {
        return addBinary(subType, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final byte[] data) {
        if (data == null) {
            return addNull();
        }
        return addBinary(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final Date timestamp) {
        if (timestamp == null) {
            return addNull();
        }
        return addTimestamp(timestamp.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final DocumentAssignable document) {
        if (document == null) {
            return addNull();
        }
        return addDocument(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final DocumentElement document) {
        if (document == null) {
            return addNull();
        }
        return addDocument(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final double value) {
        return addDouble(value);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final ElementAssignable element)
            throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add a null element.");
        }
        myElements.add(element.asElement().withName(nextIndex()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final int value) {
        return addInteger(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final long value) {
        return addLong(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final Object value) throws IllegalArgumentException {
        myElements.add(BuilderFactory.e(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final ObjectId id) {
        if (id == null) {
            return addNull();
        }
        return addObjectId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final Pattern pattern) {
        if (pattern == null) {
            return addNull();
        }
        return addRegularExpression(pattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final String value) {
        if (value == null) {
            return addNull();
        }
        return addString(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public ArrayBuilder add(final String databaseName,
            final String collectionName, final ObjectId id)
            throws IllegalArgumentException {
        return addDBPointer(databaseName, collectionName, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder add(final UUID uuid) throws IllegalArgumentException {
        if (uuid == null) {
            return addNull();
        }
        return addUuid(uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addBinary(final byte subType, final byte[] value)
            throws IllegalArgumentException {
        myElements.add(new BinaryElement(nextIndex(), subType, value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addBinary(final byte[] value)
            throws IllegalArgumentException {
        myElements.add(new BinaryElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addBoolean(final boolean value) {
        myElements.add(new BooleanElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public ArrayBuilder addDBPointer(final String databaseName,
            final String collectionName, final ObjectId id)
            throws IllegalArgumentException {
        myElements.add(new com.allanbank.mongodb.bson.element.DBPointerElement(
                nextIndex(), databaseName, collectionName, id));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addDocument(final DocumentAssignable document)
            throws IllegalArgumentException {
        myElements.add(new DocumentElement(nextIndex(), document.asDocument()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addDouble(final double value) {
        myElements.add(new DoubleElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addInteger(final int value) {
        myElements.add(new IntegerElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addJavaScript(final String code)
            throws IllegalArgumentException {
        myElements.add(new JavaScriptElement(nextIndex(), code));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addJavaScript(final String code,
            final DocumentAssignable scope) throws IllegalArgumentException {
        myElements.add(new JavaScriptWithScopeElement(nextIndex(), code, scope
                .asDocument()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addLegacyUuid(final UUID uuid)
            throws IllegalArgumentException {
        myElements.add(new UuidElement(nextIndex(),
                UuidElement.LEGACY_UUID_SUBTTYPE, uuid));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addLong(final long value) {
        myElements.add(new LongElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addMaxKey() {
        myElements.add(new MaxKeyElement(nextIndex()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addMinKey() {
        myElements.add(new MinKeyElement(nextIndex()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addMongoTimestamp(final long value) {
        myElements.add(new MongoTimestampElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addNull() {
        myElements.add(new NullElement(nextIndex()));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addObjectId(final ObjectId id)
            throws IllegalArgumentException {
        myElements.add(new ObjectIdElement(nextIndex(), id));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addRegularExpression(final Pattern pattern)
            throws IllegalArgumentException {
        myElements.add(new RegularExpressionElement(nextIndex(), pattern));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addRegularExpression(final String pattern,
            final String options) throws IllegalArgumentException {
        myElements.add(new RegularExpressionElement(nextIndex(), pattern,
                options));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addString(final String value)
            throws IllegalArgumentException {
        myElements.add(new StringElement(nextIndex(), value));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addSymbol(final String symbol)
            throws IllegalArgumentException {
        myElements.add(new SymbolElement(nextIndex(), symbol));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addTimestamp(final long timestamp) {
        myElements.add(new TimestampElement(nextIndex(), timestamp));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder addUuid(final UUID uuid)
            throws IllegalArgumentException {
        myElements.add(new UuidElement(nextIndex(), UuidElement.UUID_SUBTTYPE,
                uuid));
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to return an array of the built elements.
     * </p>
     */
    @Override
    public Element[] build() {
        final List<Element> elements = subElements();
        return elements.toArray(new Element[elements.size()]);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to return an {@link ArrayElement}.
     * </p>
     */
    @Override
    public ArrayElement build(final String name) {
        return new ArrayElement(name, subElements());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentBuilder push() {
        return doPush(nextIndex());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder pushArray() {
        return doPushArray(nextIndex());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayBuilder reset() {
        super.reset();
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to return the current state of the builder as an array
     * element.
     * </p>
     */
    @Override
    public String toString() {
        final StringWriter writer = new StringWriter();
        final JsonSerializationVisitor visitor = new JsonSerializationVisitor(
                writer, false);

        visitor.visitArray("elements", myElements);

        return writer.toString();
    }

    /**
     * Returns the next index value for an element.
     *
     * @return The next index value for an element.
     */
    private String nextIndex() {
        return ArrayElement.nameFor(myElements.size());
    }
}