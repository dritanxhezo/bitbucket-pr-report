package net.ngeor.bprr;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

@FunctionalInterface
public interface HttpClientFactory {
    HttpClient create();
}

class DefaultHttpClientFactory implements HttpClientFactory {
    @Override
    public HttpClient create() {
        return HttpClients.createDefault();
    }
}