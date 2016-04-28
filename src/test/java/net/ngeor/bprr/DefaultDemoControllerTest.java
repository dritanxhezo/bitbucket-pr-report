package net.ngeor.bprr;

import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultDemoControllerTest {
    @Test
    public void shouldLoadPullRequests() throws IOException {
        // arrange
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.set(2010, 5, 1);
        cal2.set(2011, 6, 2);
        Date dt1 = cal1.getTime();
        Date dt2 = cal2.getTime();
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[] {
                new PullRequestModel(1, "description 1", "open", dt1, dt1, "ngeor"),
                new PullRequestModel(2, "description 2", "rejected", dt2, dt2, "test", "reviewer1", "reviewer2")
        };

        BitbucketClient client = mock(BitbucketClient.class);
        when(client.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged), PullRequestsResponse.class))
                .thenReturn(new PullRequestsResponse(
                        new PullRequestResponse(1, "description 1", "open",  dt1, dt1, new Author("ngeor", "Nikolaos")),
                        new PullRequestResponse(2, "description 2", "rejected", dt2, dt2, new Author("test", "Test User")) // participants not included
                ));
        when(client.execute(new PullRequestRequest("currentUser", "repo", 1), PullRequestResponse.class))
                .thenReturn(new PullRequestResponse(1, "description 1", "open", dt1, dt1, new Author("ngeor", "Nikolaos")));
        when(client.execute(new PullRequestRequest("currentUser", "repo", 2), PullRequestResponse.class))
                .thenReturn(new PullRequestResponse(2, "description 2", "rejected", dt2, dt2, new Author("test", "Test User"),
                        new Participant(false, "role", new Author("x", "x")),
                        new Participant(true, "role", new Author("reviewer1", "Reviewer1")),
                        new Participant(true, "role", new Author("reviewer2", "Reviewer2"))));

        DefaultDemoController controller = new DefaultDemoController();
        controller.setUsername("currentUser");
        controller.setRepository("repo");
        controller.setBitbucketClient(client);

        // act
        PullRequestModel[] pullRequestModels = controller.loadPullRequests();

        // assert
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }
}
