package com.github.ngeor.http;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ngeor.json.ObjectMapperFactory;
import com.google.auto.value.AutoValue;

/**
 * Implementation of rest client.
 */
@AutoValue
public abstract class JsonHttpClientImpl implements JsonHttpClient {
    public abstract HttpClient httpClient();

    public abstract ObjectMapper objectMapper();

    public static JsonHttpClient create(HttpClient httpClient, ObjectMapper objectMapper) {
        return new AutoValue_JsonHttpClientImpl(httpClient, objectMapper);
    }

    public static JsonHttpClient create(HttpClient httpClient) {
        return create(httpClient, ObjectMapperFactory.create());
    }

    @Override
    public <E> E read(String url, final Class<E> responseType) {
        // To debug, try:
        // String s = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        try (InputStream inputStream = httpClient().read(url)) {
            return objectMapper().readValue(inputStream, responseType);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
