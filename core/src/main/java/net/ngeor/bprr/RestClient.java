package net.ngeor.bprr;

import java.io.IOException;

/**
 * A rest client.
 */
public interface RestClient { <E> E execute(Object resource, Class<E> responseType) throws IOException; }
