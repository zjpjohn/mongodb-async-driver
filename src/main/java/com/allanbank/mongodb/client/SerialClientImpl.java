/*
 * Copyright 2012, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */

package com.allanbank.mongodb.client;

import java.io.Closeable;
import java.util.logging.Logger;

import com.allanbank.mongodb.Durability;
import com.allanbank.mongodb.MongoDbConfiguration;
import com.allanbank.mongodb.MongoDbException;
import com.allanbank.mongodb.connection.Connection;

/**
 * A specialization of the {@link ClientImpl} to always try to use the same
 * connection.
 * 
 * @copyright 2012, Allanbank Consulting, Inc., All Rights Reserved
 */
public class SerialClientImpl extends AbstractClient {

    /** The logger for the {@link SerialClientImpl}. */
    protected static final Logger LOG = Logger.getLogger(SerialClientImpl.class
            .getCanonicalName());

    /** The current active Connection to the MongoDB Servers. */
    private Connection myConnection;

    /** The delegate client for accessing connections. */
    private final ClientImpl myDelegate;

    /**
     * Create a new SerialClientImpl.
     * 
     * @param client
     *            The delegate client for accessing connections.
     */
    public SerialClientImpl(final ClientImpl client) {
        myDelegate = client;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to close all of the open connections.
     * </p>
     * 
     * @see Closeable#close()
     */
    @Override
    public void close() {
        // Don't close the delegate.
        myConnection = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to return the configuration used when the client was
     * constructed.
     * </p>
     */
    @Override
    public MongoDbConfiguration getConfig() {
        return myDelegate.getConfig();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to return the configurations default durability.
     * </p>
     * 
     * @see Client#getDefaultDurability()
     */
    @Override
    public Durability getDefaultDurability() {
        return myDelegate.getDefaultDurability();
    }

    /**
     * Tries to reuse the last connection used. If the connection it closed or
     * does not exist then the request is delegated to the {@link ClientImpl}
     * and the result cached for future requests.
     * 
     * @return The found connection.
     * @throws MongoDbException
     *             On a failure to talk to the MongoDB servers.
     */
    @Override
    protected Connection findConnection() throws MongoDbException {
        if ((myConnection == null) || !myConnection.isOpen()) {
            myConnection = myDelegate.findConnection();
        }
        return myConnection;
    }
}