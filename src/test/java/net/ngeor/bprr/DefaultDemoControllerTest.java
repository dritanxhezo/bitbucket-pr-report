package net.ngeor.bprr;

import net.ngeor.dates.DateHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultDemoControllerTest {
    @Test
    public void shouldLoadPullRequests() throws IOException {
        // arrange
        Date dt1 = DateHelper.date(2010, 6, 1);
        Date dt2 = DateHelper.date(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[] {
                new PullRequestModel(1, "description 1", "open", dt1, dt1, "ngeor"),
                new PullRequestModel(2, "description 2", "rejected", dt2, dt2, "test", "reviewer1", "reviewer2")
        };

        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse firstWithoutParticipants = builder.withId(1)
                .withDescription("description 1")
                .withState("open")
                .withCreatedOn(dt1)
                .withUpdatedOn(dt1)
                .withAuthor(new Author("ngeor", "Nikolaos"))
                .build();
        PullRequestResponse firstWithParticipants = builder.withParticipants(new Participant[0]).build();

        builder.reset();
        PullRequestResponse secondWithoutParticipants = builder.withId(2)
                .withDescription("description 2")
                .withState("rejected")
                .withCreatedOn(dt2)
                .withUpdatedOn(dt2)
                .withAuthor(new Author("test", "Test User"))
                .build();
        PullRequestResponse secondWithParticipants = builder.withParticipants(new Participant[] {
                new Participant(false, "role", new Author("x", "x")),
                new Participant(true, "role", new Author("reviewer1", "Reviewer 1")),
                new Participant(true, "role", new Author("reviewer2", "Reviewer 2"))
        }).build();

        BitbucketClient client = mock(BitbucketClient.class);
        when(client.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged), PullRequestsResponse.class))
                .thenReturn(new PullRequestsResponse(firstWithoutParticipants, secondWithoutParticipants // participants not included
                ));
        when(client.execute(new PullRequestRequest("currentUser", "repo", 1), PullRequestResponse.class))
                .thenReturn(firstWithParticipants);
        when(client.execute(new PullRequestRequest("currentUser", "repo", 2), PullRequestResponse.class))
                .thenReturn(secondWithParticipants);

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
