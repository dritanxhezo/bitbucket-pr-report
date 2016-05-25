package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestRequest;
import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.Participant;
import net.ngeor.bprr.serialization.PullRequestResponse;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.util.DateHelper;
import net.ngeor.util.DateRange;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class DemoControllerImpl implements DemoController {
    private final BitbucketClient bitbucketClient;
    private final TeamMapper teamMapper;

    DemoControllerImpl(BitbucketClient bitbucketClient, TeamMapper teamMapper) {
        this.bitbucketClient = bitbucketClient;
        this.teamMapper = teamMapper;
    }

    @Override
    public PullRequestsView createView(HttpServletRequest req) throws IOException {
        String fullRepoName = req.getParameter("repo");

        // TODO: encapsulate username and repository in an object like RepositoryDescriptor
        String username = null;
        String repository = null;
        if (fullRepoName != null) {
            String[] parts = fullRepoName.split("/");
            username = parts[0];
            repository = parts[1];
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

        DateRange updatedOn = new DateRange(updatedOnFrom, updatedOnUntil);

        PullRequestModelCollection pullRequestModelCollection = this.loadPullRequests(username, repository, updatedOn);

        for (PullRequestModel pullRequestModel : pullRequestModelCollection) {
            this.teamMapper.assignTeams(pullRequestModel);
        }

        PullRequestsView view = new PullRequestsView();
        view.setFormUrl(req.getRequestURI());
        view.setRepo(fullRepoName);
        view.setPullRequests(pullRequestModelCollection);
        view.setUpdatedOnFrom(DateHelper.formatDate(updatedOnFrom));
        view.setUpdatedOnUntil(DateHelper.formatDate(updatedOnUntil));
        return view;
    }

    @NotNull
    private PullRequestModelCollection loadPullRequests(String username, String repository, DateRange updatedOn) throws IOException {
        // TODO use PullRequestClient

        // collect models here
        List<PullRequestModel> result = new ArrayList<>();

        // continue fetching pages?
        boolean fetchMorePages;

        // fetch first page
        PullRequestsRequest request = new PullRequestsRequest(username, repository, PullRequestsRequest.State.Merged, updatedOn);
        PullRequestsResponse pullRequestsResponse = bitbucketClient.execute(request, PullRequestsResponse.class);

        do {
            // store the results as models
            collectPullRequestModels(username, repository, pullRequestsResponse, result);

            // get the URL to the next page
            String next = pullRequestsResponse.getNext();

            fetchMorePages = next != null && !next.isEmpty();

            if (fetchMorePages) {
                pullRequestsResponse = bitbucketClient.execute(next, PullRequestsResponse.class);
            }
        } while (fetchMorePages);

        return new PullRequestModelCollection(result);
    }

    private void collectPullRequestModels(String username, String repository, PullRequestsResponse pullRequestsResponse, List<PullRequestModel> models) throws IOException {
        if (pullRequestsResponse == null) {
            throw new IllegalArgumentException("pullRequestsResponse cannot be null");
        }

        PullRequestResponse[] values = pullRequestsResponse.getValues();
        if (values == null) {
            throw new IllegalArgumentException("pullRequestsResponse.values cannot be null");
        }

        int[] ids = getPullRequestIds(values);
        for (int id : ids) {
            PullRequestResponse pullRequestResponse = bitbucketClient.execute(new PullRequestRequest(username, repository, id), PullRequestResponse.class);
            models.add(convertToModel(pullRequestResponse));
        }
    }

    private static int[] getPullRequestIds(PullRequestResponse... values) {
        int[] result = new int[values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = values[i].getId();
        }

        return result;
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
        List<String> result = new ArrayList<>();
        for (Participant p : participants) {
            if (p.isApproved()) {
                result.add(p.getUser().getUsername());
            }
        }

        return result.toArray(new String[result.size()]);
    }
}
