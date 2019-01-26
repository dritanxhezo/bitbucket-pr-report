package net.ngeor.bprr;

import java.io.IOException;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Implementation of rest client.
 */
public class RestClientImpl implements RestClient {
    private final SimpleHttpClient simpleHttpClient;
    private final String secret;

    public RestClientImpl(SimpleHttpClient simpleHttpClient, String secret) {
        this.simpleHttpClient = simpleHttpClient;
        this.secret           = secret;
    }

    @Override
    public <E> E execute(Object resource, final Class<E> responseType) throws IOException {
        String url = createUrl(resource);
        return simpleHttpClient.load(url, secret, inputStream -> {
            String json = IOUtils.toString(inputStream, "UTF-8");

            Gson gson = new Gson();
            try {
                return gson.fromJson(json, responseType);
            } catch (JsonSyntaxException ex) {
                throw new RuntimeException("json error: " + json);
            }
        });
    }

    protected String createUrl(Object resource) {
        return resource.toString();
    }
}
