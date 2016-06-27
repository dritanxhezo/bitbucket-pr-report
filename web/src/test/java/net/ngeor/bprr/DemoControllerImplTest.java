package net.ngeor.bprr;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequestsRequest;
import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.testutil.TestData;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DemoControllerImplTest {
    private TeamMapper teamMapper;
    private PullRequestClient pullRequestClient;
    private HttpServletRequest req;
    private PullRequest firstWithParticipants;
    private PullRequest secondWithParticipants;

    @Before
    public void before() throws IOException {
        // arrange - create mocks
        teamMapper = mock(TeamMapper.class);
        pullRequestClient = mock(PullRequestClient.class, RETURNS_SMART_NULLS);
        req = mock(HttpServletRequest.class);

        // arrange - common data for all tests
        when(req.getRequestURI()).thenReturn("/form");
        when(req.getParameter("repo")).thenReturn("currentUser/repo");
        firstWithParticipants = TestData.load(PullRequest.class, "OneParticipantNotApproved");
        secondWithParticipants = TestData.load(PullRequest.class, "ThreeParticipantsTwoApproved");

        when(pullRequestClient.loadAllDetails(new PullRequestsRequest(new RepositoryDescriptor("currentUser", "repo"), PullRequestsRequest.State.Merged, new LocalDateInterval(null, null))))
            .thenReturn(new ArrayList<PullRequest>());
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
        when(pullRequestClient.loadAllDetails(new PullRequestsRequest(
            new RepositoryDescriptor("currentUser", "repo"),
            PullRequestsRequest.State.Merged,
            new LocalDateInterval(DateHelper.localDate(2016, 5, 5), null))))
            .thenReturn(new ArrayList<PullRequest>());

        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("2016-05-05", view.getUpdatedOnFrom());
    }

    @Test
    public void shouldSetUpdatedOnFromFromMinDate() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("", view.getUpdatedOnFrom());
    }

    @Test
    public void shouldSetUpdatedOnUntil() throws IOException {
        // arrange
        when(req.getParameter("updatedOnUntil")).thenReturn("2016-05-07");
        when(pullRequestClient.loadAllDetails(new PullRequestsRequest(
            new RepositoryDescriptor("currentUser", "repo"),
            PullRequestsRequest.State.Merged,
            new LocalDateInterval(null, DateHelper.localDate(2016, 5, 7)))))
            .thenReturn(new ArrayList<PullRequest>());

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
        assertEquals("", view.getUpdatedOnUntil());
    }

    @Test
    public void shouldLoadPullRequestsWhenThereIsOnlyOnePageOfResults() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");

        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[]{
            new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
            new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        expectedPullRequestModels[0].setReviewerTeams(new String[]{null, null});
        expectedPullRequestModels[1].setReviewerTeams(new String[]{null, null});

        when(pullRequestClient.loadAllDetails(new PullRequestsRequest(
            new RepositoryDescriptor("currentUser", "repo"),
            PullRequestsRequest.State.Merged,
            new LocalDateInterval(DateHelper.localDate(2016, 5, 5), null))))
            .thenReturn(Arrays.asList(firstWithParticipants, secondWithParticipants));

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

        Date dt1 = DateHelper.utcDate(2010, 6, 1);
        Date dt2 = DateHelper.utcDate(2011, 7, 2);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[]{
            new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
            new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")
        };

        expectedPullRequestModels[0].setAuthorTeam("team1");
        expectedPullRequestModels[0].setReviewerTeams(new String[]{null, null});
        expectedPullRequestModels[1].setReviewerTeams(new String[]{null, "team2"});

        when(teamMapper.userToTeam("mfrauenholtz")).thenReturn("team1");
        when(teamMapper.userToTeam("reviewer 1")).thenReturn("team2");

        when(pullRequestClient.loadAllDetails(new PullRequestsRequest(
            new RepositoryDescriptor("currentUser", "repo"),
            PullRequestsRequest.State.Merged,
            new LocalDateInterval(DateHelper.localDate(2016, 5, 5), null))))
            .thenReturn(Arrays.asList(firstWithParticipants, secondWithParticipants));

        // act
        PullRequestsView view = createView();

        // assert
        PullRequestModel[] pullRequestModels = view.getPullRequests().toArray();
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }

    @NotNull
    private DemoControllerImpl createDefaultDemoController() {
        return new DemoControllerImpl(pullRequestClient, teamMapper);
    }

    @NotNull
    private PullRequestsView createView() throws IOException {
        return createDefaultDemoController().createView(req);
    }
}
