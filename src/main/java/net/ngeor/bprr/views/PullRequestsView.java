package net.ngeor.bprr.views;

import net.ngeor.bprr.PullRequestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PullRequestsView {
    private PullRequestModel[] pullRequests;
    private String formUrl;
    private String repo;
    private String updatedOn;

    public void setPullRequests(PullRequestModel[] pullRequests) {
        this.pullRequests = pullRequests;
        normalize();
    }

    private void normalize() {
        if (pullRequests == null) {
            return;
        }

        int maxReviewerCount = getMaxReviewerCount();
        for (PullRequestModel model : pullRequests) {
            normalize(model, maxReviewerCount);
        }
    }

    private void normalize(PullRequestModel model, int maxReviewerCount) {
        if (maxReviewerCount <= 0) {
            return;
        }

        String[] reviewers = model.getReviewers();
        if (reviewers.length > maxReviewerCount) {
            throw new IllegalStateException();
        }

        if (reviewers.length == maxReviewerCount) {
            return;
        }

        model.setReviewers(Arrays.copyOf(reviewers, maxReviewerCount));
    }

    public PullRequestModel[] getPullRequests() {
        return pullRequests;
    }

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

        return result.toArray(new Statistics[result.size()]);
    }

    private void increaseReviewers(List<Statistics> result, String reviewerTeam) {
        Statistics statistic = findOrCreate(result, reviewerTeam);
        statistic.setReviewed(1 + statistic.getReviewed());
    }

    private void increaseAuthors(List<Statistics> result, String authorTeam) {
        Statistics statistic = findOrCreate(result, authorTeam);
        statistic.setCreated(1 + statistic.getCreated());
    }

    private Statistics findOrCreate(List<Statistics> items, String team) {
        Statistics result = null;
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

    public int getMaxReviewerCount() {
        int result = 0;
        if (pullRequests != null) {
            for (PullRequestModel model : pullRequests) {
                String[] reviewers = model.getReviewers();
                if (reviewers != null) {
                    result = Math.max(result, reviewers.length);
                }
            }
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

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestsView that = (PullRequestsView) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(pullRequests, that.pullRequests)) return false;
        if (formUrl != null ? !formUrl.equals(that.formUrl) : that.formUrl != null) return false;
        if (repo != null ? !repo.equals(that.repo) : that.repo != null) return false;
        return updatedOn != null ? updatedOn.equals(that.updatedOn) : that.updatedOn == null;

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(pullRequests);
        result = 31 * result + (formUrl != null ? formUrl.hashCode() : 0);
        result = 31 * result + (repo != null ? repo.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

    public static class Statistics {
        private String name;
        private int created;
        private int reviewed;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public int getReviewed() {
            return reviewed;
        }

        public void setReviewed(int reviewed) {
            this.reviewed = reviewed;
        }
    }
}
