package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.Participant;
import net.ngeor.bprr.serialization.PullRequest;
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
        RepositoryDescriptor repositoryDescriptor = RepositoryDescriptor.parse(fullRepoName);

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

        PullRequestModelCollection pullRequestModelCollection = this.loadPullRequests(repositoryDescriptor, updatedOn);

        for (PullRequestModel pullRequestModel : pullRequestModelCollection) {
            assignTeams(pullRequestModel);
        }

        PullRequestsView view = new PullRequestsView();
        view.setFormUrl(req.getRequestURI());
        view.setRepo(fullRepoName);
        view.setPullRequests(pullRequestModelCollection);
        view.setUpdatedOnFrom(DateHelper.formatDate(updatedOnFrom));
        view.setUpdatedOnUntil(DateHelper.formatDate(updatedOnUntil));
        return view;
    }

    private void assignTeams(PullRequestModel pullRequestModel) {
        pullRequestModel.setAuthorTeam(teamMapper.userToTeam(pullRequestModel.getAuthor()));
        String[] reviewers = pullRequestModel.getReviewers();
        String[] reviewerTeams = new String[reviewers.length];
        for (int i = 0; i < reviewers.length; i++) {
            reviewerTeams[i] = teamMapper.userToTeam(reviewers[i]);
        }

        pullRequestModel.setReviewerTeams(reviewerTeams);
    }

    @NotNull
    private PullRequestModelCollection loadPullRequests(RepositoryDescriptor repositoryDescriptor, DateRange updatedOn) throws IOException {
        // fetch pull requests
        PullRequestsRequest request = new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Merged, updatedOn);
        List<PullRequest> pullRequests = pullRequestClient.loadAllDetails(request);

        // collect models here
        List<PullRequestModel> result = new ArrayList<>();
        for (PullRequest pullRequest : pullRequests) {
            result.add(convertToModel(pullRequest));
        }

        return new PullRequestModelCollection(result);
    }

    private PullRequestModel convertToModel(PullRequest pullRequest) {
        PullRequestModel model = new PullRequestModel();
        model.setId(pullRequest.getId());
        model.setDescription(pullRequest.getDescription());
        model.setState(pullRequest.getState());
        model.setCreatedOn(pullRequest.getCreatedOn());
        model.setUpdatedOn(pullRequest.getUpdatedOn());
        model.setAuthor(pullRequest.getAuthor().getUsername());
        model.setReviewers(convertReviewers(pullRequest.getParticipants()));
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
