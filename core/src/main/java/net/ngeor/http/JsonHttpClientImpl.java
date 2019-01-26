package net.ngeor.http;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ngeor.json.ObjectMapperFactory;

/**
 * Implementation of rest client.
 */
public class JsonHttpClientImpl implements JsonHttpClient {
    private final HttpClient simpleHttpClient;
    private final ObjectMapper objectMapper;

    /**
     * Creates an instance of this class.
     *
     * @param simpleHttpClient The HTTP client.
     */
    public JsonHttpClientImpl(HttpClient simpleHttpClient) {
        this.simpleHttpClient = simpleHttpClient;
        this.objectMapper     = ObjectMapperFactory.create();
    }

    @Override
    public <E> E read(String url, final Class<E> responseType) throws IOException {
        try (InputStream inputStream = simpleHttpClient.read(url)) {
            return this.objectMapper.readValue(inputStream, responseType);
        }
    }
}
