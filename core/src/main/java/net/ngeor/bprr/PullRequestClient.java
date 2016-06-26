package net.ngeor.bprr;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;
import net.ngeor.bitbucket.PullRequestsRequest;

import java.io.IOException;
import java.util.List;

public interface PullRequestClient {
    PullRequests load(PullRequestsRequest request) throws IOException;

    List<PullRequests> loadAllPages(PullRequestsRequest request) throws IOException;

    List<PullRequest> loadDetails(PullRequests pullRequests) throws IOException;

    List<PullRequest> loadAllDetails(PullRequestsRequest request) throws IOException;
}
