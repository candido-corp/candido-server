package com.candido.server.config;

public final class ConfigTemporaryCode {

    /**
     * This represents the max size of the pool for the temporary code.
     */
    public static final int MIN_TEMPORARY_CODE_POOL_SIZE = 20;

    /**
     * This represents the max size of a single temporary code.
     * This is intended to be MAX - 1 because the random algorithm exclude the last value.
     * Basically if the number is 1_000_000 the code would be of 6 digit.
     */
    public static final int MAX_TEMPORARY_CODE_SIZE = 1_000_000;

    /**
     * This represents the max number of seconds (S) that the code can be active.
     */
    public static final int MAX_TEMPORARY_CODE_DURATION = 300;

}
