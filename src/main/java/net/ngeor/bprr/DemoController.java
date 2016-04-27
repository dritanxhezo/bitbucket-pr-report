package net.ngeor.bprr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface DemoController {
    PullRequestModel[] loadPullRequests() throws IOException;
    void setBitbucketClient(BitbucketClient bitbucketClient);
    void setUsername(String username);
    void setRepository(String repository);
}

class DefaultDemoController implements DemoController {
    private String username;
    private String repository;
    private BitbucketClient bitbucketClient;

    @Override
    public PullRequestModel[] loadPullRequests() throws IOException {
        // TODO fetch more pages from the paginated response
        PullRequestsResponse pullRequestsResponse = bitbucketClient.execute(new PullRequestsRequest(username, repository), PullRequestsResponse.class);
        int[] ids = Arrays.stream(pullRequestsResponse.getValues()).mapToInt(v -> v.getId()).toArray();
        List<PullRequestModel> result = new ArrayList<>();
        for (int id : ids) {
            PullRequestResponse pullRequestResponse = bitbucketClient.execute(new PullRequestRequest(username, repository, id), PullRequestResponse.class);
            result.add(convertToModel(pullRequestResponse));
        }

        return result.toArray(new PullRequestModel[result.size()]);
    }

    private PullRequestModel convertToModel(PullRequestResponse pullRequestResponse) {
        return new PullRequestModel(
                pullRequestResponse.getId(),
                pullRequestResponse.getDescription(),
                pullRequestResponse.getState(),
                pullRequestResponse.getAuthor().getUsername(),
                convertReviewers(pullRequestResponse.getParticipants()));
    }

    private String[] convertReviewers(Participant[] participants) {
        return Arrays.stream(participants)
                .filter(p -> p.isApproved())
                .map(p -> p.getUser().getUsername())
                .toArray(String[]::new);
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Override
    public void setBitbucketClient(BitbucketClient bitbucketClient) {
        this.bitbucketClient = bitbucketClient;
    }
}