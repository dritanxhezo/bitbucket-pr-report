package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequest;
import net.ngeor.bprr.serialization.PullRequests;

import java.io.IOException;
import java.util.List;

public interface PullRequestClient {
    PullRequests load(PullRequestsRequest request) throws IOException;

    List<PullRequests> loadAllPages(PullRequestsRequest request) throws IOException;

    List<PullRequest> loadDetails(PullRequests pullRequests) throws IOException;

    List<PullRequest> loadAllDetails(PullRequestsRequest request) throws IOException;
}
