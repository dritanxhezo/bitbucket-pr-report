package net.ngeor.bprr;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.ngeor.bitbucket.Participant;
import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequestsRequest;
import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;

/**
 * Implementation of {@link DemoController}.
 */
class DemoControllerImpl implements DemoController {
    private final PullRequestClient pullRequestClient;
    private final TeamMapper teamMapper;

    DemoControllerImpl(PullRequestClient pullRequestClient, TeamMapper teamMapper) {
        this.pullRequestClient = pullRequestClient;
        this.teamMapper        = teamMapper;
    }

    @Override
    public PullRequestsView createView(HttpServletRequest req) throws IOException {
        String fullRepoName                       = req.getParameter("repo");
        RepositoryDescriptor repositoryDescriptor = RepositoryDescriptor.parse(fullRepoName);

        LocalDate updatedOnFrom;
        try {
            updatedOnFrom = DateHelper.parseLocalDate(req.getParameter("updatedOnFrom"));
        } catch (ParseException e) {
            updatedOnFrom = null;
        }

        LocalDate updatedOnUntil;
        try {
            updatedOnUntil = DateHelper.parseLocalDate(req.getParameter("updatedOnUntil"));
        } catch (ParseException e) {
            updatedOnUntil = null;
        }

        LocalDateInterval updatedOn = new LocalDateInterval(updatedOnFrom, updatedOnUntil);

        PullRequestModelCollection pullRequestModelCollection = this.loadPullRequests(repositoryDescriptor, updatedOn);

        for (PullRequestModel pullRequestModel : pullRequestModelCollection) {
            assignTeams(pullRequestModel);
        }

        PullRequestsView view = new PullRequestsView();
        view.setFormUrl(req.getRequestURI());
        view.setRepo(fullRepoName);
        view.setPullRequests(pullRequestModelCollection);
        view.setUpdatedOnFrom(updatedOnFrom != null ? updatedOnFrom.toString() : "");
        view.setUpdatedOnUntil(updatedOnUntil != null ? updatedOnUntil.toString() : "");
        return view;
    }

    private void assignTeams(PullRequestModel pullRequestModel) {
        pullRequestModel.setAuthorTeam(teamMapper.userToTeam(pullRequestModel.getAuthor()));
        String[] reviewers     = pullRequestModel.getReviewers();
        String[] reviewerTeams = new String[reviewers.length];
        for (int i = 0; i < reviewers.length; i++) {
            reviewerTeams[i] = teamMapper.userToTeam(reviewers[i]);
        }

        pullRequestModel.setReviewerTeams(reviewerTeams);
    }

    private PullRequestModelCollection loadPullRequests(RepositoryDescriptor repositoryDescriptor,
                                                        LocalDateInterval updatedOn) throws IOException {
        // fetch pull requests
        PullRequestsRequest request =
            new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Merged, updatedOn);
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
