package net.ngeor.bprr.views;

import net.ngeor.bprr.PullRequestModel;
import net.ngeor.util.DateHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PullRequestsViewTest {
    @Test
    public void shouldReturn2Reviewers() {
        PullRequestsView view = new PullRequestsView();
        view.setPullRequests(new PullRequestModel[] {
                new PullRequestModel(1, "hey", "merged", DateHelper.utcToday(), DateHelper.utcToday(), "ngeor", "rev1", "rev2")
        });

        assertEquals(2, view.getMaxReviewerCount());
    }

    @Test
    public void shouldReturn4Reviewers() {
        PullRequestsView view = new PullRequestsView();
        view.setPullRequests(new PullRequestModel[] {
                new PullRequestModel(1, "hey", "merged", DateHelper.utcToday(), DateHelper.utcToday(), "ngeor", "rev1", "rev2"),
                new PullRequestModel(2, "hey", "merged", DateHelper.utcToday(), DateHelper.utcToday(), "ngeor", "rev1", "rev2", "rev3", "rev4")
        });

        assertEquals(4, view.getMaxReviewerCount());
    }
}
