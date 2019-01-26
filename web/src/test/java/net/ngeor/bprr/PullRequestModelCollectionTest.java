package net.ngeor.bprr;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link PullRequestModelCollection}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class PullRequestModelCollectionTest {
    @Test
    public void shouldReturn2Reviewers() {
        PullRequestModelCollection view =
            new PullRequestModelCollection(Arrays.asList(new PullRequestModel().withReviewers("rev1", "rev2")));

        assertEquals(2, view.getMaxReviewerCount());
        assertEquals(2, view.toArray()[0].getReviewers().length);
    }

    @Test
    public void shouldReturn4Reviewers() {
        PullRequestModelCollection view = new PullRequestModelCollection(
            Arrays.asList(new PullRequestModel().withReviewers("rev1", "rev2"),
                          new PullRequestModel().withReviewers("rev1", "rev2", "rev3", "rev4")));

        assertEquals(4, view.getMaxReviewerCount());
        assertEquals(4, view.toArray()[0].getReviewers().length);
    }
}
