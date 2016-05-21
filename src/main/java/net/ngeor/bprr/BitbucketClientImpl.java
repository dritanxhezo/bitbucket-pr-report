package net.ngeor.bprr;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BitbucketClientImpl implements BitbucketClient {
    private String accessToken;
    private final HttpClientFactory httpClientFactory;

    public BitbucketClientImpl(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    public BitbucketClientImpl() {
        this(new HttpClientFactoryImpl());
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public <E> E execute(Object resource, Class<E> responseType) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }

        HttpClient httpClient = httpClientFactory.create();
        try {
            String url = createUrl(resource);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse == null) {
                throw new NullPointerException("Null HTTP response");
            }

            try {
                InputStream content = httpResponse.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(content);
                Gson gson = new Gson();
                return gson.fromJson(reader, responseType);
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
        result.append((result.indexOf("?") > 0) ? '&' : '?');
        result.append("access_token=");
        result.append(getAccessToken());
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
            try {
                ((Closeable) x).close();
            } catch (IOException e) {
            }
        }
    }
}
