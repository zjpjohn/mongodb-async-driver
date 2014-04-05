/*
 * Copyright 2011-2013, Allanbank Consulting, Inc.
 *           All Rights Reserved
 */

package com.allanbank.mongodb.client.message;

import com.allanbank.mongodb.bson.Document;
import com.allanbank.mongodb.bson.builder.BuilderFactory;
import com.allanbank.mongodb.bson.builder.DocumentBuilder;
import com.allanbank.mongodb.bson.impl.ImmutableDocument;

/**
 * Provides a convenient mechanism for creating a <a href=
 * "http://docs.mongodb.org/manual/reference/command/replSetGetStatus/"
 * >replSetGetStatus</a> command.
 *
 * @api.no This class is <b>NOT</b> part of the drivers API. This class may be
 *         mutated in incompatible ways between any two releases of the driver.
 * @copyright 2011-2013, Allanbank Consulting, Inc., All Rights Reserved
 */
public class ReplicaSetStatus extends AdminCommand {

    /** The serverStatus "query" document. */
    public static final Document REPLICA_SET_STATUS;

    static {
        final DocumentBuilder builder = BuilderFactory.start();
        builder.addInteger("replSetGetStatus", 1);
        REPLICA_SET_STATUS = new ImmutableDocument(builder);
    }

    /**
     * Create a new ReplicaSetStatus command.
     */
    public ReplicaSetStatus() {
        super(REPLICA_SET_STATUS);
    }
}
