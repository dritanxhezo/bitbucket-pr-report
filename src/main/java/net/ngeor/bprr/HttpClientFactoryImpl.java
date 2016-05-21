package net.ngeor.bprr;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

class HttpClientFactoryImpl implements HttpClientFactory {
    @Override
    public HttpClient create() {
        return HttpClients.createDefault();
    }
}
