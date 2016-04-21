package net.ngeor.bprr;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RepositoriesRequest {
    public RepositoriesResponse execute(String user, String accessToken) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("https://api.bitbucket.org/2.0/repositories/" + user + "?access_token=" + accessToken);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            try {
                InputStream content = httpResponse.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(content);
                RepositoriesResponseFactory repositoriesResponseFactory = new RepositoriesResponseFactory();
                RepositoriesResponse repositoriesResponse = repositoriesResponseFactory.parse(reader);
                return repositoriesResponse;
            } finally {
                httpResponse.close();
            }
        } finally {
            httpClient.close();
        }
    }
}
