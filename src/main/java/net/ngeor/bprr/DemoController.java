package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestRequest;
import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.Participant;
import net.ngeor.bprr.serialization.PullRequestResponse;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.util.DateHelper;
import net.ngeor.util.DateRange;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface DemoController {
    PullRequestsView createView(HttpServletRequest req) throws IOException;
}

class DefaultDemoController implements DemoController {
    private String username;
    private String repository;
    private BitbucketClient bitbucketClient;
    private DateRange updatedOn;
    private final BitbucketClientFactory bitbucketClientFactory;
    private final TeamMapper teamMapper;

    public DefaultDemoController(BitbucketClientFactory bitbucketClientFactory, TeamMapper teamMapper) {
        this.bitbucketClientFactory = bitbucketClientFactory;
        this.teamMapper = teamMapper;
    }

    @Override
    public PullRequestsView createView(HttpServletRequest req) throws IOException {
        BitbucketClient bitbucketClient = bitbucketClientFactory.createClient(req);
        this.bitbucketClient = bitbucketClient;
        String fullRepoName = req.getParameter("repo");
        if (fullRepoName != null) {
            String[] parts = fullRepoName.split("\\/");
            this.setUsername(parts[0]);
            this.setRepository(parts[1]);
        }

        Date updatedOnFrom;
        try {
            updatedOnFrom = DateHelper.parseUtcDate(req.getParameter("updatedOnFrom"));
        } catch (ParseException e) {
            updatedOnFrom = DateHelper.utcToday();
        }

        Date updatedOnUntil;
        try {
            updatedOnUntil = DateHelper.parseUtcDate(req.getParameter("updatedOnUntil"));
        } catch (ParseException e) {
            updatedOnUntil = DateHelper.utcToday();
        }

        this.setUpdatedOn(new DateRange(updatedOnFrom, updatedOnUntil));

        PullRequestModel[] pullRequests = this.loadPullRequests();
        if (pullRequests != null) {
            this.teamMapper.loadFromProperties();

            for (PullRequestModel pullRequestModel : pullRequests) {
                this.teamMapper.assignTeams(pullRequestModel);
            }
        }

        PullRequestsView view = new PullRequestsView();
        view.setFormUrl(req.getRequestURI());
        view.setRepo(fullRepoName);
        view.setPullRequests(pullRequests);
        view.setUpdatedOnFrom(DateHelper.formatDate(updatedOnFrom));
        view.setUpdatedOnUntil(DateHelper.formatDate(updatedOnUntil));
        return view;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setRepository(String repository) {
        this.repository = repository;
    }

    private void setUpdatedOn(DateRange updatedOn) {
        this.updatedOn = updatedOn;
    }

    private PullRequestModel[] loadPullRequests() throws IOException {
        // collect models here
        List<PullRequestModel> result = new ArrayList<>();

        // continue fetching pages?
        boolean fetchMorePages;

        // fetch first page
        PullRequestsRequest request = new PullRequestsRequest(username, repository, PullRequestsRequest.State.Merged, updatedOn);
        PullRequestsResponse pullRequestsResponse = bitbucketClient.execute(request, PullRequestsResponse.class);

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
        if (pullRequestsResponse == null) {
            throw new IllegalArgumentException("pullRequestsResponse cannot be null");
        }

        PullRequestResponse[] values = pullRequestsResponse.getValues();
        if (values == null) {
            throw new IllegalArgumentException("pullRequestsResponse.values cannot be null");
        }

        int[] ids = Arrays.stream(values).mapToInt(v -> v.getId()).toArray();
        for (int id : ids) {
            PullRequestResponse pullRequestResponse = bitbucketClient.execute(new PullRequestRequest(username, repository, id), PullRequestResponse.class);
            models.add(convertToModel(pullRequestResponse));
        }
    }

    private PullRequestModel convertToModel(PullRequestResponse pullRequestResponse) {
        PullRequestModel model = new PullRequestModel();
        model.setId(pullRequestResponse.getId());
        model.setDescription(pullRequestResponse.getDescription());
        model.setState(pullRequestResponse.getState());
        model.setCreatedOn(pullRequestResponse.getCreatedOn());
        model.setUpdatedOn(pullRequestResponse.getUpdatedOn());
        model.setAuthor(pullRequestResponse.getAuthor().getUsername());
        model.setReviewers(convertReviewers(pullRequestResponse.getParticipants()));
        return model;
    }

    private String[] convertReviewers(Participant[] participants) {
        return Arrays.stream(participants)
                .filter(p -> p.isApproved())
                .map(p -> p.getUser().getUsername())
                .toArray(String[]::new);
    }
}