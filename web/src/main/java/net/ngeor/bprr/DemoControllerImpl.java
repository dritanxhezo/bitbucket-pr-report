package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.Participant;
import net.ngeor.bprr.serialization.PullRequestResponse;
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
    private final PullRequestClient pullRequestClient;
    private final TeamMapper teamMapper;

    DemoControllerImpl(PullRequestClient pullRequestClient, TeamMapper teamMapper) {
        this.pullRequestClient = pullRequestClient;
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
        // fetch pull requests
        PullRequestsRequest request = new PullRequestsRequest(username, repository, PullRequestsRequest.State.Merged, updatedOn);
        List<PullRequestResponse> pullRequestResponses = pullRequestClient.loadAllDetails(request);

        // collect models here
        List<PullRequestModel> result = new ArrayList<>();
        for (PullRequestResponse pullRequestResponse : pullRequestResponses) {
            result.add(convertToModel(pullRequestResponse));
        }

        return new PullRequestModelCollection(result);
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
