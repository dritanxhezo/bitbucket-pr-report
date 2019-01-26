package net.ngeor.bprr;

import java.io.IOException;
import java.util.List;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;
import net.ngeor.http.JsonHttpClient;

import static net.ngeor.bprr.PageCollector.collectAll;

/**
 * Implementation of {@link PullRequestClient}.
 */
public class PullRequestClientImpl implements PullRequestClient {
    private final JsonHttpClient bitbucketClient;

    public PullRequestClientImpl(JsonHttpClient bitbucketClient) {
        this.bitbucketClient = bitbucketClient;
    }

    @Override
    public PullRequests load(PullRequestsRequest request) throws IOException {
        String url = "https://api.bitbucket.org/2.0/" + request.toString();
        return bitbucketClient.read(url, PullRequests.class);
    }

    @Override
    public List<PullRequest> loadAllPages(PullRequestsRequest request) throws IOException {
        PullRequests response = load(request);
        return collectAll(bitbucketClient, response, PullRequests.class);
    }

    @Override
    public PullRequest loadDetails(PullRequest partialResponse) throws IOException {
        String href = partialResponse.getLinks().getSelf().getHref();
        return bitbucketClient.read(href, PullRequest.class);
    }

    @Override
    public List<PullRequest> loadAllDetails(PullRequestsRequest request) throws IOException {
        List<PullRequest> partials = loadAllPages(request);
        for (int i = 0; i < partials.size(); i++) {
            partials.set(i, loadDetails(partials.get(i)));
        }

        return partials;
    }
}
