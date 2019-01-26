package net.ngeor.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple http client.
 */
public interface HttpClient {
    /**
     * Performs a GET operation on the given URL.
     *
     * @param url The url.
     * @return The stream.
     * @throws IOException if an error occurs.
     */
    InputStream read(String url) throws IOException;
}
