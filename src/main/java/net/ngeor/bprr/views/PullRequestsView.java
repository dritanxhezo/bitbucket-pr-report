package net.ngeor.bprr.views;

import net.ngeor.bprr.PullRequestModel;

import java.util.Arrays;
import java.util.Date;

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
}
