package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestRequest;
import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestResponse;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.testutil.TestData;
import net.ngeor.util.DateHelper;
import net.ngeor.util.DateRange;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DemoControllerImplTest {
    private BitbucketClientFactory bitbucketClientFactory;
    private TeamMapper teamMapper;
    private BitbucketClient bitbucketClient;
    private HttpServletRequest req;
    private PullRequestsResponse emptyResponse;

    @Before
    public void before() throws IOException {
        // arrange - create mocks
        bitbucketClientFactory = mock(BitbucketClientFactory.class);
        teamMapper = mock(TeamMapper.class);
        bitbucketClient = mock(BitbucketClient.class, RETURNS_SMART_NULLS);
        req = mock(HttpServletRequest.class);

        // arrange - mock relationships
        when(bitbucketClientFactory.createClient(req)).thenReturn(bitbucketClient);

        // arrange - common data for all tests
        when(req.getRequestURI()).thenReturn("/form");
        when(req.getParameter("repo")).thenReturn("currentUser/repo");
        PullRequestResponse firstWithParticipants = TestData.load(PullRequestResponse.class, "OneParticipantNotApproved");
        PullRequestResponse secondWithParticipants = TestData.load(PullRequestResponse.class, "ThreeParticipantsTwoApproved");
        when(bitbucketClient.execute(new PullRequestRequest("currentUser", "repo", 1), PullRequestResponse.class))
                .thenReturn(firstWithParticipants);
        when(bitbucketClient.execute(new PullRequestRequest("currentUser", "repo", 2), PullRequestResponse.class))
                .thenReturn(secondWithParticipants);

        emptyResponse = new PullRequestsResponse();
        when(bitbucketClient.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, new DateRange(DateHelper.utcToday(), DateHelper.utcToday())), PullRequestsResponse.class))
                .thenReturn(emptyResponse);
    }

    @Test
    public void shouldSetFormUrl() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("/form", view.getFormUrl());
    }

    @Test
    public void shouldSetFullRepo() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("currentUser/repo", view.getRepo());
    }

    @Test
    public void shouldSetUpdatedOnFrom() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");
        when(bitbucketClient.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, new DateRange(DateHelper.utcDate(2016, 5, 5), DateHelper.utcToday())), PullRequestsResponse.class))
                .thenReturn(emptyResponse);

        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("2016-05-05", view.getUpdatedOnFrom());
    }

    @Test
    public void shouldSetUpdatedOnFromFromCurrentDate() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals(DateHelper.formatDate(DateHelper.utcToday()), view.getUpdatedOnFrom());
    }

    @Test
    public void shouldSetUpdatedOnUntil() throws IOException {
        // arrange
        when(req.getParameter("updatedOnUntil")).thenReturn("2016-05-07");
        when(bitbucketClient.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, new DateRange(DateHelper.utcToday(), DateHelper.utcDate(2016, 5, 7))), PullRequestsResponse.class))
                .thenReturn(emptyResponse);

        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("2016-05-07", view.getUpdatedOnUntil());
    }

    @Test
    public void shouldSetUpdatedOnUntilFromCurrentDate() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals(DateHelper.formatDate(DateHelper.utcToday()), view.getUpdatedOnUntil());
    }

    @Test
    public void shouldLoadPullRequestsWhenThereIsOnlyOnePageOfResults() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");

        PullRequestsResponse pullRequestsResponse = TestData.load(PullRequestsResponse.class, "NoPagination");

        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[]{
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        when(bitbucketClient.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, new DateRange(DateHelper.utcDate(2016, 5, 5), DateHelper.utcToday())), PullRequestsResponse.class))
                .thenReturn(pullRequestsResponse);

        // act
        PullRequestsView view = createView();

        // assert
        PullRequestModel[] pullRequestModels = view.getPullRequests().toArray();
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }

    @Test
    public void shouldUseTeamMapperToAssignTeams() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");

        PullRequestsResponse pullRequestsResponse = TestData.load(PullRequestsResponse.class, "NoPagination");

        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[]{
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        when(bitbucketClient.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, new DateRange(DateHelper.utcDate(2016, 5, 5), DateHelper.utcToday())), PullRequestsResponse.class))
                .thenReturn(pullRequestsResponse);

        // act
        PullRequestsView view = createView();

        // assert
        PullRequestModel[] pullRequestModels = view.getPullRequests().toArray();
        verify(teamMapper).assignTeams(expectedPullRequestModels[0]);
        verify(teamMapper).assignTeams(expectedPullRequestModels[1]);
    }

    @Test
    public void shouldCollectAllPages() throws IOException {
        // arrange
        when(req.getParameter("updatedOnUntil")).thenReturn("2016-05-04");

        PullRequestsResponse firstPullRequestsResponse = TestData.load(PullRequestsResponse.class, "Pagination");
        PullRequestsResponse secondPullRequestsResponse = TestData.load(PullRequestsResponse.class, "NoPagination");


        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[]{
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1"),
                new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
                new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        when(bitbucketClient.execute(new PullRequestsRequest("currentUser", "repo", PullRequestsRequest.State.Merged, new DateRange(DateHelper.utcToday(), DateHelper.utcDate(2016, 5, 4))), PullRequestsResponse.class))
                .thenReturn(firstPullRequestsResponse);
        when(bitbucketClient.execute("https://api.bitbucket.org/2.0/pullrequests/2", PullRequestsResponse.class))
                .thenReturn(secondPullRequestsResponse);

        // act
        PullRequestsView view = createView();

        // assert
        PullRequestModel[] pullRequestModels = view.getPullRequests().toArray();
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }

    @NotNull
    private DemoControllerImpl createDefaultDemoController() {
        return new DemoControllerImpl(bitbucketClientFactory, teamMapper);
    }

    @NotNull
    private PullRequestsView createView() throws IOException {
        return createDefaultDemoController().createView(req);
    }
}
