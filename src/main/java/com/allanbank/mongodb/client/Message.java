/*
 * #%L
 * Message.java - mongodb-async-driver - Allanbank Consulting, Inc.
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
package com.allanbank.mongodb.client;

import java.io.IOException;

import com.allanbank.mongodb.ReadPreference;
import com.allanbank.mongodb.Version;
import com.allanbank.mongodb.bson.io.BsonOutputStream;
import com.allanbank.mongodb.bson.io.BufferingBsonOutputStream;
import com.allanbank.mongodb.error.DocumentToLargeException;
import com.allanbank.mongodb.error.ServerVersionException;

/**
 * Common interface for all MongoDB messages read from and sent to a MongoDB
 * server.
 *
 * @api.no This class is <b>NOT</b> part of the drivers API. This class may be
 *         mutated in incompatible ways between any two releases of the driver.
 * @copyright 2011-2013, Allanbank Consulting, Inc., All Rights Reserved
 */
public interface Message {

    /**
     * Returns the name of the collection.
     *
     * @return The name of the collection.
     */
    public String getCollectionName();

    /**
     * Returns the name of the database.
     *
     * @return The name of the database.
     */
    public String getDatabaseName();

    /**
     * Returns a short name for the operation.
     *
     * @return A short name for the operation.
     */
    public String getOperationName();

    /**
     * Provides the details on which servers are eligible to receive the
     * message.
     *
     * @return The {@link ReadPreference} for which servers should be sent the
     *         request.
     */
    public ReadPreference getReadPreference();

    /**
     * Returns the required version range for the message.
     * <p>
     * This may be {@code null} which should be interpreted to mean that all
     * versions of the server support the message's operation. In reality that
     * is probably more accurately stated as all supported versions.
     * </p>
     *
     * @return The version of the server that introduced support for the
     *         operation.
     */
    public VersionRange getRequiredVersionRange();

    /**
     * Returns the total size of the message on the wire.
     *
     * @return The size of the message on the wire.
     */
    public int size();

    /**
     * Transforms the message to, potentially, a different operation that is
     * optimized for the specific server version.
     * <p>
     * The best example of this method's usage is the ListCollectionsMessage and
     * ListIndexesMessage. Both of these operations changed in MongoDB 2.7.7
     * from queries on special collections to actual commands to fully support
     * pluggable storage engines. This method allows the message to be
     * transformed based on the version of the server it is being sent to.
     * </p>
     * For most commands this method will return the same message. </p>
     *
     * @param serverVersion
     *            The version of the server the message will be sent to.
     * @return The transformed message optimized for the server's version. Most
     *         likely the same message but never <code>null</code>.
     * @throws ServerVersionException
     *             On the message not being supported on the specified server
     *             version.
     */
    public Message transformFor(Version serverVersion)
            throws ServerVersionException;

    /**
     * Validates that the documents with the message do not exceed the maximum
     * document size specified.
     *
     * @param maxDocumentSize
     *            The maximum document size to validate against.
     *
     * @throws DocumentToLargeException
     *             If one of the documents in the message is too large or the
     *             documents in aggregate are too large.
     */
    public void validateSize(int maxDocumentSize)
            throws DocumentToLargeException;

    /**
     * Writes the message from the stream. The message header <b>is</b> written
     * by this method.
     *
     * @param messageId
     *            The id to be assigned to the message.
     * @param out
     *            The sink for data written.
     * @throws IOException
     *             On an error writing to the stream.
     */
    public void write(int messageId, BsonOutputStream out) throws IOException;

    /**
     * Writes the message from the stream. The message header <b>is</b> written
     * by this method.
     *
     * @param messageId
     *            The id to be assigned to the message.
     * @param out
     *            The sink for data written.
     * @throws IOException
     *             On an error writing to the stream.
     */
    public void write(int messageId, BufferingBsonOutputStream out)
            throws IOException;
}
