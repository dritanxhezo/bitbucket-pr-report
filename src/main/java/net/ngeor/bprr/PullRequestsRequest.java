package net.ngeor.bprr;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PullRequestsRequest {
    private String owner;
    private String repositorySlug;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepositorySlug() {
        return repositorySlug;
    }

    public void setRepositorySlug(String repositorySlug) {
        this.repositorySlug = repositorySlug;
    }

    private String createUrl() {
        String url = "https://api.bitbucket.org/2.0/repositories/" + owner + "/" + repositorySlug + "/pullrequests";
        url = url + "?access_token=" + accessToken;
        return url;
    }

    public PullRequestsResponse execute() throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String url = createUrl();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            try {
                InputStream content = httpResponse.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(content);
                PullRequestsResponseFactory pullRequestsResponseFactory = new PullRequestsResponseFactory();
                PullRequestsResponse pullRequestsResponse = pullRequestsResponseFactory.parse(reader);
                return pullRequestsResponse;
            } finally {
                httpResponse.close();
            }
        } finally {
            httpClient.close();
        }
    }
}
