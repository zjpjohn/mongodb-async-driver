/*
 * Copyright 2012, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */

/**
 * Provides a Java driver for the MongoDB document store that allows asynchronous invocation of requests.
 *
 * <h2>Package Layout / High Level Design</h2>
 * <p>
 * The <tt>com.allanbank.mongodb</tt> package contains the interfaces and basic classes that the client code
 * will use to interact with the driver.
 * </p>
 * <p>
 * The <a href="bson/package-summary.html">com.allanbank.mongodb.bson</a> package contains a strongly typed, 
 * immutable implementation of the <a href="http://bsonspec.org/">BSON Specification</a>.
 * </p>
 * <p>
 * In the <a href="client/package-summary.html">com.allanbank.mongodb.client</a> package are the implementation 
 * of the primary interfaces for the driver and support classes to convert 
 * {@link com.allanbank.mongodb.connection.message.Reply}(s) from the server into a more user friendly format.
 * </p>
 * <p>
 * To facilitate constructing the more complex commands to the MongoDB servers a set of support classes are 
 * provided in the <a href="commands/package-summary.html">com.allanbank.mongodb.commands</a> package.
 * </p>
 * <p>
 * Exceptions (all inheriting from {@link com.allanbank.mongodb.MongoDbException} are located in the 
 * <a href="error/package-summary.html">com.allanbank.mongodb.error</a> package.
 * </p>
 * <p>
 * Lastly, the <a href="connection/package-summary.html">com.allanbank.mongodb.connection</a> package contains the 
 * heart of the driver.  Most users of the driver will not need to use any of the classes in this package.
 * </p>
 * 
 * @copyright 2012, Allanbank Consulting, Inc., All Rights Reserved
 */
package com.allanbank.mongodb;
