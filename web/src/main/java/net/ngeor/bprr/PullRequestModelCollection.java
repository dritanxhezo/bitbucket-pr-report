package net.ngeor.bprr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of pull request models.
 */
public class PullRequestModelCollection implements Iterable<PullRequestModel> {
    private final ArrayList<PullRequestModel> pullRequestModels = new ArrayList<>();

    /**
     * Creates an instance of this class.
     * @param pullRequestModels
     */
    public PullRequestModelCollection(List<PullRequestModel> pullRequestModels) {
        if (pullRequestModels != null) {
            this.pullRequestModels.addAll(pullRequestModels);

            normalize();
        }
    }

    /**
     * Gets the max reviewer count.
     * @return
     */
    public int getMaxReviewerCount() {
        int result = 0;
        for (PullRequestModel model : pullRequestModels) {
            String[] reviewers = model.getReviewers();
            if (reviewers != null) {
                result = Math.max(result, reviewers.length);
            }
        }

        return result;
    }

    @Override
    public Iterator<PullRequestModel> iterator() {
        return pullRequestModels.iterator();
    }

    private void normalize() {
        int maxReviewerCount = getMaxReviewerCount();
        for (PullRequestModel model : pullRequestModels) {
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

    public PullRequestModel[] toArray() {
        return pullRequestModels.toArray(new PullRequestModel[pullRequestModels.size()]);
    }
}
