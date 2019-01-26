package net.ngeor.http;

import java.io.IOException;

/**
 * An HTTP client that deserializes from json.
 */
public interface JsonHttpClient {
    /**
     * Performs a GET operation on the given URL and deserializes
     * the JSON response.
     * @param url The URL.
     * @param responseType The class of the response.
     * @param <E> The type of the response.
     * @return The deserialized result.
     * @throws IOException If an error occurs.
     */
    <E> E read(String url, Class<E> responseType) throws IOException;
}
