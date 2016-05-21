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

public class BitbucketClientImpl implements BitbucketClient {
    private final HttpClientFactory httpClientFactory;
    private final Settings settings;

    public BitbucketClientImpl(HttpClientFactory httpClientFactory, Settings settings) {
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

    private String createUrl(Object resource) {
        if (isBitbucketUrl(resource)) {
            return (String) resource;
        }

        StringBuilder result = new StringBuilder();
        result.append("https://api.bitbucket.org/2.0/");
        result.append(resource);
        return result.toString();
    }

    private boolean isBitbucketUrl(Object resource) {
        if (!(resource instanceof String)) {
            return false;
        }

        String url = (String) resource;
        return url.startsWith("https://api.bitbucket.org/2.0/");
    }

    private void safeClose(Object x) {
        if (x instanceof Closeable) {
            IOUtils.closeQuietly((Closeable) x);
        }
    }
}
