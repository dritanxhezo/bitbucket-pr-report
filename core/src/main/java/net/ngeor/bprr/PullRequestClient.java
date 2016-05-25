package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestsResponse;

import java.io.IOException;
import java.util.List;

public interface PullRequestClient {
    PullRequestsResponse load(PullRequestsRequest request) throws IOException;

    List<PullRequestsResponse> loadAllPages(PullRequestsRequest request) throws IOException;

    // TODO List<PullRequestResponse> loadDetails(List<PullRequestsResponse>)
}
