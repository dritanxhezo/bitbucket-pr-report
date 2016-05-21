package net.ngeor.bprr;

import org.apache.http.client.HttpClient;

public interface HttpClientFactory {
    HttpClient create();
}

