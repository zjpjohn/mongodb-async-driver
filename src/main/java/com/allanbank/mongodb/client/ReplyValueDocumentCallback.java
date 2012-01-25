/*
 * Copyright 2011, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */

package com.allanbank.mongodb.client;

import java.util.List;

import com.allanbank.mongodb.Callback;
import com.allanbank.mongodb.MongoDbException;
import com.allanbank.mongodb.bson.Document;
import com.allanbank.mongodb.bson.element.DocumentElement;
import com.allanbank.mongodb.connection.messsage.Reply;
import com.allanbank.mongodb.error.ReplyException;

/**
 * Callback to expect and extract a single document from the reply and then
 * extract its contained "value" document.
 * 
 * @copyright 2011, Allanbank Consulting, Inc., All Rights Reserved
 */
/* package */class ReplyValueDocumentCallback extends
        AbstractReplyCallback<Document> {

    /**
     * Create a new ReplyDocumentCallback.
     * 
     * @param results
     *            The callback to notify of the reply document.
     */
    public ReplyValueDocumentCallback(final Callback<Document> results) {
        super(results);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates an exception if the {@link Reply} has less than or more than a
     * single reply document.
     * </p>
     * 
     * @param reply
     *            The raw reply.
     * @return The exception created.
     */
    @Override
    protected MongoDbException asError(final Reply reply) {
        MongoDbException error = super.asError(reply);
        if (error == null) {
            final List<Document> results = reply.getResults();
            if (results.size() != 1) {
                error = new ReplyException(reply,
                        "Should only be a single document in the reply.");
            }
            else if (reply.getResults().get(0)
                    .queryPath(DocumentElement.class, "value").isEmpty()) {
                error = new ReplyException(reply,
                        "No value document in the reply.");
            }
        }
        return error;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to return the reply document.
     * </p>
     * 
     * @see AbstractReplyCallback#convert(Reply)
     */
    @Override
    protected Document convert(final Reply reply) throws MongoDbException {
        final List<Document> results = reply.getResults();
        if (results.size() == 1) {
            return results.get(0).queryPath(DocumentElement.class, "value")
                    .get(0);
        }

        return null;
    }
}