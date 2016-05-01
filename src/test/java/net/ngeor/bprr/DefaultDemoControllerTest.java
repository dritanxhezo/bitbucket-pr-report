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
        PullRequestsResponse pullRequestsResponse = TestData.load(PullRequestsResponse.class, "NoPagination");
        PullRequestResponse firstWithParticipants = TestData.load(PullRequestResponse.class, "OneParticipantNotApproved");
        PullRequestResponse secondWithParticipants = TestData.load(PullRequestResponse.class, "ThreeParticipantsTwoApproved");

        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[] {
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz"),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        BitbucketClient client = mock(BitbucketClient.class);
        when(client.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged), PullRequestsResponse.class))
                .thenReturn(pullRequestsResponse);
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
