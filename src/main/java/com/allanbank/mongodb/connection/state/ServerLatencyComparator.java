/*
 * Copyright 2011, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */
package com.allanbank.mongodb.connection.state;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares {@link ServerState}s based on the latency of the servers.
 * 
 * @copyright 2011, Allanbank Consulting, Inc., All Rights Reserved
 */
public class ServerLatencyComparator implements Comparator<ServerState>,
        Serializable {

    /** A singleton instance of the comparator. No need to multiple instances. */
    public static final Comparator<ServerState> COMPARATOR = new ServerLatencyComparator();

    /** Serialization version of the class. */
    private static final long serialVersionUID = -7926757327660948536L;

    /**
     * Creates a new {@link ServerLatencyComparator}.
     */
    private ServerLatencyComparator() {
        super();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Compares the servers based on their respective average latencies.
     * </p>
     * 
     * @see Comparator#compare
     */
    @Override
    public int compare(final ServerState o1, final ServerState o2) {
        return Double.compare(o1.getAverageLatency(), o2.getAverageLatency());
    }

}
