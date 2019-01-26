package net.ngeor.bprr;

import java.io.IOException;
import java.util.List;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;

/**
 * A pull request client.
 */
public interface PullRequestClient {
    PullRequests load(PullRequestsRequest request) throws IOException;

    List<PullRequest> loadAllPages(PullRequestsRequest request) throws IOException;

    PullRequest loadDetails(PullRequest partialInstance) throws IOException;

    List<PullRequest> loadAllDetails(PullRequestsRequest request) throws IOException;
}
