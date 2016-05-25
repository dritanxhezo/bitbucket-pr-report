package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PullRequestClientImpl implements PullRequestClient {
    private final BitbucketClient bitbucketClient;

    public PullRequestClientImpl(BitbucketClient bitbucketClient) {
        this.bitbucketClient = bitbucketClient;
    }

    @Override
    public PullRequestsResponse load(PullRequestsRequest request) throws IOException {
        return bitbucketClient.execute(request, PullRequestsResponse.class);
    }

    @Override
    public List<PullRequestsResponse> loadAllPages(PullRequestsRequest request) throws IOException {
        List<PullRequestsResponse> result = new ArrayList<>();
        PullRequestsResponse response = load(request);
        result.add(response);
        String next = response.getNext();
        while (!StringUtils.isBlank(next)) {
            response = bitbucketClient.execute(next, PullRequestsResponse.class);
            next = response.getNext();
            result.add(response);
        }

        return result;
    }
}
