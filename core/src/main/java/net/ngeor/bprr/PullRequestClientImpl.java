package net.ngeor.bprr;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;
import net.ngeor.bitbucket.PullRequestsRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PullRequestClientImpl implements PullRequestClient {
    private final RestClient bitbucketClient;

    public PullRequestClientImpl(RestClient bitbucketClient) {
        this.bitbucketClient = bitbucketClient;
    }

    @Override
    public PullRequests load(PullRequestsRequest request) throws IOException {
        return bitbucketClient.execute(request, PullRequests.class);
    }

    @Override
    public List<PullRequests> loadAllPages(PullRequestsRequest request) throws IOException {
        List<PullRequests> result = new ArrayList<>();
        PullRequests response = load(request);
        result.add(response);
        String next = response.getNext();
        while (!StringUtils.isBlank(next)) {
            response = bitbucketClient.execute(next, PullRequests.class);
            next = response.getNext();
            result.add(response);
        }

        return result;
    }

    @Override
    public List<PullRequest> loadDetails(PullRequests pullRequests) throws IOException {
        List<PullRequest> result = new ArrayList<>();
        for (PullRequest partialResponse : pullRequests.getValues()) {
            String href = partialResponse.getLinks().getSelf().getHref();
            PullRequest fullResponse = bitbucketClient.execute(href, PullRequest.class);
            result.add(fullResponse);
        }

        return result;
    }

    @Override
    public List<PullRequest> loadAllDetails(PullRequestsRequest request) throws IOException {
        List<PullRequests> pullRequestsList = loadAllPages(request);
        List<PullRequest> result = new ArrayList<>();
        for (PullRequests pullRequests : pullRequestsList) {
            result.addAll(loadDetails(pullRequests));
        }

        return result;
    }
}
