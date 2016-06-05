package net.ngeor.bprr;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class RestClientImpl implements RestClient {
    private final HttpClientFactory httpClientFactory;
    private final Settings settings;

    public RestClientImpl(HttpClientFactory httpClientFactory, Settings settings) {
        this.httpClientFactory = httpClientFactory;
        this.settings = settings;
    }

    @Override
    public <E> E execute(Object resource, Class<E> responseType) throws IOException {
        HttpClient httpClient = httpClientFactory.create();
        try {
            String url = createUrl(resource);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", "Basic " + settings.getSecret());
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse == null) {
                throw new NullPointerException("Null HTTP response");
            }

            try {
                InputStream content = httpResponse.getEntity().getContent();
                String json = IOUtils.toString(content, "UTF-8");

                Gson gson = new Gson();
                try {
                    return gson.fromJson(json, responseType);
                } catch (JsonSyntaxException ex) {
                    throw new RuntimeException("json error: " + json);
                }
            } finally {
                safeClose(httpResponse);
            }
        } finally {
            safeClose(httpClient);
        }
    }

    protected String createUrl(Object resource) {
        return resource.toString();
    }

    private void safeClose(Object x) {
        if (x instanceof Closeable) {
            IOUtils.closeQuietly((Closeable) x);
        }
    }

}
