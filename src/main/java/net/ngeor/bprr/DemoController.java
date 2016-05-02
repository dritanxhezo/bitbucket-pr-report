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

    @Override
    public PullRequestModel[] loadPullRequests() throws IOException {
        // collect models here
        List<PullRequestModel> result = new ArrayList<>();

        // continue fetching pages?
        boolean fetchMorePages;

        // fetch first page
        PullRequestsResponse pullRequestsResponse = bitbucketClient.execute(new PullRequestsRequest(username, repository, PullRequestsRequest.State.Merged), PullRequestsResponse.class);

        int pages = 0;

        do {
            // store the results as models
            collectPullRequestModels(pullRequestsResponse, result);

            // get the URL to the next page
            String next = pullRequestsResponse.getNext();

            // TODO: stop if target date is reached otherwise it will fetch a lot of data
            fetchMorePages = next != null && !next.isEmpty() && pages < 6;

            if (fetchMorePages) {
                pullRequestsResponse = bitbucketClient.execute(next, PullRequestsResponse.class);
                pages++;
            }
        } while (fetchMorePages);

        return result.toArray(new PullRequestModel[result.size()]);
    }

    private void collectPullRequestModels(PullRequestsResponse pullRequestsResponse, List<PullRequestModel> models) throws IOException {
        int[] ids = Arrays.stream(pullRequestsResponse.getValues()).mapToInt(v -> v.getId()).toArray();
        for (int id : ids) {
            PullRequestResponse pullRequestResponse = bitbucketClient.execute(new PullRequestRequest(username, repository, id), PullRequestResponse.class);
            models.add(convertToModel(pullRequestResponse));
        }
    }

    private PullRequestModel convertToModel(PullRequestResponse pullRequestResponse) {
        return new PullRequestModel(
                pullRequestResponse.getId(),
                pullRequestResponse.getDescription(),
                pullRequestResponse.getState(),
                pullRequestResponse.getCreatedOn(),
                pullRequestResponse.getUpdatedOn(),
                pullRequestResponse.getAuthor().getUsername(),
                convertReviewers(pullRequestResponse.getParticipants()));
    }

    private String[] convertReviewers(Participant[] participants) {
        return Arrays.stream(participants)
                .filter(p -> p.isApproved())
                .map(p -> p.getUser().getUsername())
                .toArray(String[]::new);
    }
}