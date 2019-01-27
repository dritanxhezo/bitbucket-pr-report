package net.ngeor.bprr.views;

import java.util.ArrayList;
import java.util.List;

import net.ngeor.bprr.PullRequestModel;
import net.ngeor.bprr.PullRequestModelCollection;

/**
 * View of pull requests.
 */
public class PullRequestsView {
    private PullRequestModelCollection pullRequests;
    private String formUrl;
    private String repo;
    private String updatedOnFrom;
    private String updatedOnUntil;

    public PullRequestModelCollection getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(PullRequestModelCollection pullRequests) {
        this.pullRequests = pullRequests;
    }

    /**
     * Gets the statistics.
     * @return
     */
    public Statistics[] getStatistics() {
        List<Statistics> result = new ArrayList<>();

        if (pullRequests != null) {
            for (PullRequestModel model : pullRequests) {
                String authorTeam = model.getAuthorTeam();
                increaseAuthors(result, authorTeam);

                for (String reviewerTeam : model.getReviewerTeams()) {
                    increaseReviewers(result, reviewerTeam);
                }
            }
        }

        return result.toArray(new Statistics[0]);
    }

    private void increaseReviewers(List<Statistics> result, String reviewerTeam) {
        if (reviewerTeam == null) {
            return;
        }

        Statistics statistic = findOrCreate(result, reviewerTeam);
        statistic.setReviewed(1 + statistic.getReviewed());
    }

    private void increaseAuthors(List<Statistics> result, String authorTeam) {
        Statistics statistic = findOrCreate(result, authorTeam);
        statistic.setCreated(1 + statistic.getCreated());
    }

    private Statistics findOrCreate(List<Statistics> items, String team) {
        Statistics result = null;

        if (team == null || team.isEmpty()) {
            throw new IllegalArgumentException("team cannot be empty");
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(team)) {
                result = items.get(i);
                break;
            }
        }

        if (result == null) {
            result = new Statistics();
            result.setName(team);
            items.add(result);
        }

        return result;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getUpdatedOnFrom() {
        return updatedOnFrom;
    }

    public void setUpdatedOnFrom(String updatedOnFrom) {
        this.updatedOnFrom = updatedOnFrom;
    }

    public String getUpdatedOnUntil() {
        return updatedOnUntil;
    }

    public void setUpdatedOnUntil(String updatedOnUntil) {
        this.updatedOnUntil = updatedOnUntil;
    }
}
