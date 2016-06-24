package net.ngeor.bprr;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

class SimpleHttpClientImpl implements SimpleHttpClient {
    @Override
    public <E> E load(String url, String basicAuthenticationHeader, InputStreamClient<E> inputStreamClient) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", "Basic " + basicAuthenticationHeader);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse == null) {
                throw new NullPointerException("Null HTTP response");
            }

            try {
                InputStream content = httpResponse.getEntity().getContent();
                return inputStreamClient.consume(content);
            } finally {
                safeClose(httpResponse);
            }
        } finally {
            safeClose(httpClient);
        }
    }

    private void safeClose(Object x) {
        if (x instanceof Closeable) {
            IOUtils.closeQuietly((Closeable) x);
        }
    }
}
