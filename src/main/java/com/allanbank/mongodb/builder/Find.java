/*
 * Copyright 2012, Allanbank Consulting, Inc. 
 *           All Rights Reserved
 */

package com.allanbank.mongodb.builder;

import com.allanbank.mongodb.ReadPreference;
import com.allanbank.mongodb.bson.Document;
import com.allanbank.mongodb.bson.DocumentAssignable;

/**
 * Find provides an immutable container for all of the options for a query.
 * 
 * @copyright 2012, Allanbank Consulting, Inc., All Rights Reserved
 */
public class Find {

    /** The number of documents to be returned in each batch of results. */
    private final int myBatchSize;

    /** The total number of documents to be returned. */
    private final int myLimit;

    /** The number of documents to skip before returning the first document. */
    private final int myNumberToSkip;

    /** If true then an error in the query should return any partial results. */
    private final boolean myPartialOk;

    /** The query document. */
    private final Document myQuery;

    /** The preference for which servers to use to retrieve the results. */
    private final ReadPreference myReadPreference;

    /** The fields to be returned from the matching documents. */
    private final Document myReturnFields;

    /** The fields to order the document by. */
    private final Document mySortFields;

    /**
     * Creates a new Find.
     * 
     * @param builder
     *            The builder to copy the query fields from.
     */
    protected Find(final Builder builder) {
        myQuery = builder.myQuery;
        myReturnFields = builder.myReturnFields;
        myBatchSize = builder.myBatchSize;
        myLimit = builder.myLimit;
        myNumberToSkip = builder.myNumberToSkip;
        myPartialOk = builder.myPartialOk;
        myReadPreference = builder.myReadPreference;
        mySortFields = builder.mySortFields;
    }

    /**
     * Returns the number of documents to be returned in each batch of results.
     * 
     * @return The number of documents to be returned in each batch of results.
     */
    public int getBatchSize() {
        return myBatchSize;
    }

    /**
     * Returns the total number of documents to be returned.
     * 
     * @return The total number of documents to be returned.
     */
    public int getLimit() {
        return myLimit;
    }

    /**
     * Returns the number of documents to skip before returning the first
     * document.
     * 
     * @return The number of documents to skip before returning the first
     *         document.
     */
    public int getNumberToSkip() {
        return myNumberToSkip;
    }

    /**
     * Returns the query document.
     * 
     * @return The query document.
     */
    public Document getQuery() {
        return myQuery;
    }

    /**
     * Returns the preference for the servers to retrieve the results from. May
     * be <code>null</code> in which case the default read preference should be
     * used.
     * 
     * @return The preference for the servers to retrieve the results from.
     */
    public ReadPreference getReadPreference() {
        return myReadPreference;
    }

    /**
     * Returns the fields to be returned from the matching documents.
     * 
     * @return The fields to be returned from the matching documents.
     */
    public Document getReturnFields() {
        return myReturnFields;
    }

    /**
     * Returns the fields to order document by.
     * 
     * @return The fields to order document by.
     */
    public Document getSortFields() {
        return mySortFields;
    }

    /**
     * Returns the partial okay value. If true then an error in the query should
     * return any partial results.
     * 
     * @return The partial okay value. If true then an error in the query should
     *         return any partial results.
     */
    public boolean isPartialOk() {
        return myPartialOk;
    }

    /**
     * Helper for creating immutable {@link Find} queries.
     * 
     * @copyright 2011, Allanbank Consulting, Inc., All Rights Reserved
     */
    public static class Builder {
        /** The number of documents to be returned in each batch of results. */
        protected int myBatchSize;

        /** The total number of documents to be returned. */
        protected int myLimit;

        /** The number of documents to skip before returning the first document. */
        protected int myNumberToSkip;

        /**
         * If true then an error in the query should return any partial results.
         */
        protected boolean myPartialOk;

        /** The query document. */
        protected Document myQuery;

        /** The preference for which servers to use to retrieve the results. */
        protected ReadPreference myReadPreference;

        /** The fields to be returned from the matching documents. */
        protected Document myReturnFields;

        /** The fields to order the document on. */
        protected Document mySortFields;

        /**
         * Creates a new Builder.
         */
        public Builder() {
            myQuery = null;
            myReturnFields = null;
            myBatchSize = 0;
            myLimit = 0;
            myNumberToSkip = 0;
            myPartialOk = false;
            myReadPreference = null;
        }

        /**
         * Creates a new Builder.
         * 
         * @param query
         *            The query document.
         */
        public Builder(final DocumentAssignable query) {
            this();
            myQuery = query.asDocument();
        }

        /**
         * Constructs a new {@link Find} object from the state of the builder.
         * 
         * @return The new {@link Find} object.
         */
        public Find build() {
            return new Find(this);
        }

        /**
         * Sets the value of the number of documents to be returned in each
         * batch.
         * 
         * @param batchSize
         *            The new value for the number of documents to be returned
         *            in each batch.
         * @return This builder for chaining method calls.
         */
        public Builder setBatchSize(final int batchSize) {
            myBatchSize = batchSize;
            return this;
        }

        /**
         * Sets the value of the total number of documents to be returned.
         * 
         * @param limit
         *            The new value for the total number of documents to be
         *            returned.
         * @return This builder for chaining method calls.
         */
        public Builder setLimit(final int limit) {
            myLimit = limit;
            return this;
        }

        /**
         * Sets the value of the number of documents to skip before returning
         * the first document to the new value.
         * 
         * @param numberToSkip
         *            The new value for the number of documents to skip before
         *            returning the first document.
         * @return This builder for chaining method calls.
         */
        public Builder setNumberToSkip(final int numberToSkip) {
            myNumberToSkip = numberToSkip;
            return this;
        }

        /**
         * Sets the value of partial okay to the new value. If true then an
         * error in the query should return any partial results.
         * 
         * @param partialOk
         *            The new value for the partial okay.
         * @return This builder for chaining method calls.
         */
        public Builder setPartialOk(final boolean partialOk) {
            myPartialOk = partialOk;
            return this;
        }

        /**
         * Sets the value of the query document to the new value.
         * 
         * @param query
         *            The new value for the query document.
         * @return This builder for chaining method calls.
         */
        public Builder setQuery(final DocumentAssignable query) {
            myQuery = query.asDocument();
            return this;
        }

        /**
         * Sets the preference for the set of servers to retrieve the results
         * from.
         * 
         * @param readPreference
         *            The new value for the preference of which server to return
         *            the results from.
         * @return This builder for chaining method calls.
         */
        public Builder setReadPreference(final ReadPreference readPreference) {
            myReadPreference = readPreference;
            return this;
        }

        /**
         * Sets the value of the fields to be returned from the matching
         * documents to the new value.
         * 
         * @param returnFields
         *            The new value for the fields to be returned from the
         *            matching documents.
         * @return This builder for chaining method calls.
         */
        public Builder setReturnFields(final DocumentAssignable returnFields) {
            myReturnFields = returnFields.asDocument();
            return this;
        }

        /**
         * Sets the value of the fields to to sort matching documents by.
         * 
         * @param sortFields
         *            The new value for the fields to sort matching documents
         *            by.
         * @return This builder for chaining method calls.
         */
        public Builder setSortFields(final DocumentAssignable sortFields) {
            mySortFields = sortFields.asDocument();
            return this;
        }
    }
}
