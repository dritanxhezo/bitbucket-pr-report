package net.ngeor.bprr;

import java.io.IOException;

/**
 * Basic HTTP client that supports basic authentication.
 */
public interface SimpleHttpClient {
    /**
     * Loads the given URL with the specified basic authentication header
     * and sends it to the designated input stream consumer.
     * @param url The URL to load.
     * @param basicAuthenticationHeader The basic authentication header.
     * @param inputStreamClient The consumer of the response.
     * @param <E> The return type.
     * @return The result of the input stream client.
     * @throws IOException
     */
    <E> E load(String url, String basicAuthenticationHeader, InputStreamClient<E> inputStreamClient) throws IOException;
}

