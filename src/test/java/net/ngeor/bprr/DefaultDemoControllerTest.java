package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestRequest;
import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestResponse;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import net.ngeor.testutil.TestData;
import net.ngeor.util.DateHelper;
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
        when(client.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, DateHelper.utcDate(2016, 5, 5)), PullRequestsResponse.class))
                .thenReturn(pullRequestsResponse);
        when(client.execute(new PullRequestRequest("currentUser", "repo", 1), PullRequestResponse.class))
                .thenReturn(firstWithParticipants);
        when(client.execute(new PullRequestRequest("currentUser", "repo", 2), PullRequestResponse.class))
                .thenReturn(secondWithParticipants);

        DefaultDemoController controller = new DefaultDemoController();
        controller.setUsername("currentUser");
        controller.setRepository("repo");
        controller.setUpdatedOn(DateHelper.utcDate(2016, 5, 5));
        controller.setBitbucketClient(client);

        // act
        PullRequestModel[] pullRequestModels = controller.loadPullRequests();

        // assert
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }

    @Test
    public void shouldCollectAllPages() throws IOException {
        // arrange
        PullRequestsResponse firstPullRequestsResponse = TestData.load(PullRequestsResponse.class, "Pagination");
        PullRequestsResponse secondPullRequestsResponse = TestData.load(PullRequestsResponse.class, "NoPagination");
        PullRequestResponse firstWithParticipants = TestData.load(PullRequestResponse.class, "OneParticipantNotApproved");
        PullRequestResponse secondWithParticipants = TestData.load(PullRequestResponse.class, "ThreeParticipantsTwoApproved");

        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[] {
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz"),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1"),
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz"),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        BitbucketClient client = mock(BitbucketClient.class);
        when(client.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, DateHelper.utcDate(2016, 5, 4)), PullRequestsResponse.class))
                .thenReturn(firstPullRequestsResponse);
        when(client.execute("https://api.bitbucket.org/2.0/pullrequests/2", PullRequestsResponse.class))
                .thenReturn(secondPullRequestsResponse);
        when(client.execute(new PullRequestRequest("currentUser", "repo", 1), PullRequestResponse.class))
                .thenReturn(firstWithParticipants);
        when(client.execute(new PullRequestRequest("currentUser", "repo", 2), PullRequestResponse.class))
                .thenReturn(secondWithParticipants);

        DefaultDemoController controller = new DefaultDemoController();
        controller.setUsername("currentUser");
        controller.setRepository("repo");
        controller.setUpdatedOn(DateHelper.utcDate(2016, 5, 4));
        controller.setBitbucketClient(client);

        // act
        PullRequestModel[] pullRequestModels = controller.loadPullRequests();

        // assert
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }
}
