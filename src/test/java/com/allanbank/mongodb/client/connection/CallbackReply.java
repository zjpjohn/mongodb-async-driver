/*
 * Copyright 2012-2014, Allanbank Consulting, Inc.
 *           All Rights Reserved
 */

package com.allanbank.mongodb.client.connection;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import com.allanbank.mongodb.CallbackCapture;
import com.allanbank.mongodb.bson.Document;
import com.allanbank.mongodb.bson.builder.DocumentBuilder;
import com.allanbank.mongodb.client.callback.ReplyCallback;
import com.allanbank.mongodb.client.message.Reply;

/**
 * CallbackReply provides the ability to trigger the callback when called from
 * an {@link EasyMock} mock.
 *
 * @copyright 2012-2014, Allanbank Consulting, Inc., All Rights Reserved
 */
public class CallbackReply {

    /**
     * Creates a new CallbackReply.
     *
     * @param builders
     *            The reply to provide to the callback.
     * @return The CallbackReply.
     */
    public static ReplyCallback cb(final DocumentBuilder... builders) {
        CallbackCapture.callback(reply(builders));
        return null;
    }

    /**
     * Creates a new CallbackReply.
     *
     * @param reply
     *            The reply to provide to the callback.
     * @return The CallbackReply.
     */
    public static ReplyCallback cb(final Reply reply) {
        CallbackCapture.callback(reply);
        return null;
    }

    /**
     * Creates a new CallbackReply.
     *
     * @param error
     *            The error to provide to the callback.
     * @return The CallbackReply.
     */
    public static ReplyCallback cb(final Throwable error) {
        CallbackCapture.callback(error);
        return null;
    }

    /**
     * Creates a new CallbackReply.
     *
     * @return The CallbackReply.
     */
    public static ReplyCallback cbError() {
        CallbackCapture.callbackError();
        return null;
    }

    /**
     * Creates a reply with the specified document.
     *
     * @param builders
     *            The builder for the reply document.
     * @return The Repy.
     */
    public static Reply reply(final DocumentBuilder... builders) {
        final List<Document> docs = new ArrayList<Document>(builders.length);
        for (final DocumentBuilder builder : builders) {
            docs.add(builder.build());
        }
        return new Reply(0, 0, 0, docs, false, false, false, false);
    }

    /**
     * Creates a new CallbackReply.
     */
    private CallbackReply() {
    }
}
