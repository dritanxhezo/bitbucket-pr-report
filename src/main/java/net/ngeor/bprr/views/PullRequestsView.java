package net.ngeor.bprr.views;

import net.ngeor.bprr.PullRequestModel;

public class PullRequestsView {
    private PullRequestModel[] pullRequests;

    public void setPullRequests(PullRequestModel[] pullRequests) {
        this.pullRequests = pullRequests;
    }

    public PullRequestModel[] getPullRequests() {
        return pullRequests;
    }

    public int getMaxReviewerCount() {
        int result = 0;
        if (pullRequests != null) {
            for (PullRequestModel model : pullRequests) {
                result = Math.max(result, model.getReviewers().length);
            }
        }

        return result;
    }
}
