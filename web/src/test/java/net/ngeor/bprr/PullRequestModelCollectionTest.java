package net.ngeor.bprr;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PullRequestModelCollectionTest {
    @Test
    public void shouldReturn2Reviewers() {
        PullRequestModelCollection view = new PullRequestModelCollection(
                Arrays.asList(
                        new PullRequestModel().withReviewers("rev1", "rev2")
                ));

        assertEquals(2, view.getMaxReviewerCount());
        assertEquals(2, view.toArray()[0].getReviewers().length);
    }

    @Test
    public void shouldReturn4Reviewers() {
        PullRequestModelCollection view = new PullRequestModelCollection(
                Arrays.asList(
                        new PullRequestModel().withReviewers("rev1", "rev2"),
                        new PullRequestModel().withReviewers("rev1", "rev2", "rev3", "rev4")
                ));

        assertEquals(4, view.getMaxReviewerCount());
        assertEquals(4, view.toArray()[0].getReviewers().length);
    }
}
