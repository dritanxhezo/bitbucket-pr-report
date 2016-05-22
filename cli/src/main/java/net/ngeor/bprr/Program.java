package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestsResponse;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar:*" net.ngeor.bprr.Program user secret repositorySlug
        final String user = args[0];
        final String secret = args[1];
        final String repositorySlug = args[2];

        // echo mini.local bitbucket.open.pull.requests 0 | zabbix_sender -z localhost -vv -i -
        HttpClientFactory httpClientFactory = new HttpClientFactoryImpl();
        Settings settings = new Settings() {
            @Override
            public String getUser() {
                return user;
            }

            @Override
            public String getSecret() {
                return secret;
            }
        };
        BitbucketClient bitbucketClient = new BitbucketClientImpl(httpClientFactory, settings);
        PullRequestsRequest request = new PullRequestsRequest(user, repositorySlug, PullRequestsRequest.State.Open);
        PullRequestsResponse pullRequestsResponse = bitbucketClient.execute(request, PullRequestsResponse.class);

        // generate output that can be used with zabbix_sender
        System.out.println("mini.local bitbucket.open.pull.requests " + pullRequestsResponse.getSize());
    }
}
