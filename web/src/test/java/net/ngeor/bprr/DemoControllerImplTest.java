package net.ngeor.bprr;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.testutil.TestData;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DemoControllerImpl}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class DemoControllerImplTest {
    private TeamMapper teamMapper;
    private PullRequestClient pullRequestClient;
    private HttpServletRequest req;
    private PullRequest firstWithParticipants;
    private PullRequest secondWithParticipants;

    @BeforeEach
    void before() throws IOException {
        // arrange - create mocks
        teamMapper        = mock(TeamMapper.class);
        pullRequestClient = mock(PullRequestClient.class, RETURNS_SMART_NULLS);
        req               = mock(HttpServletRequest.class);

        // arrange - common data for all tests
        when(req.getRequestURI()).thenReturn("/form");
        when(req.getParameter("repo")).thenReturn("currentUser/repo");
        firstWithParticipants  = TestData.load(PullRequest.class, "OneParticipantNotApproved");
        secondWithParticipants = TestData.load(PullRequest.class, "ThreeParticipantsTwoApproved");

        when(pullRequestClient.loadAllDetails(new PullRequestsRequest(new RepositoryDescriptor("currentUser", "repo"),
                                                                      PullRequestsRequest.State.Merged,
                                                                      new LocalDateInterval(null, null))))
            .thenReturn(new ArrayList<>());
    }

    @Test
    void shouldSetFormUrl() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("/form", view.getFormUrl());
    }

    @Test
    void shouldSetFullRepo() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("currentUser/repo", view.getRepo());
    }

    @Test
    void shouldSetUpdatedOnFrom() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");
        when(pullRequestClient.loadAllDetails(
                 new PullRequestsRequest(new RepositoryDescriptor("currentUser", "repo"),
                                         PullRequestsRequest.State.Merged,
                                         new LocalDateInterval(DateHelper.localDate(2016, 5, 5), null))))
            .thenReturn(new ArrayList<PullRequest>());

        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("2016-05-05", view.getUpdatedOnFrom());
    }

    @Test
    void shouldSetUpdatedOnFromFromMinDate() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("", view.getUpdatedOnFrom());
    }

    @Test
    void shouldSetUpdatedOnUntil() throws IOException {
        // arrange
        when(req.getParameter("updatedOnUntil")).thenReturn("2016-05-07");
        when(pullRequestClient.loadAllDetails(
                 new PullRequestsRequest(new RepositoryDescriptor("currentUser", "repo"),
                                         PullRequestsRequest.State.Merged,
                                         new LocalDateInterval(null, DateHelper.localDate(2016, 5, 7)))))
            .thenReturn(new ArrayList<PullRequest>());

        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("2016-05-07", view.getUpdatedOnUntil());
    }

    @Test
    void shouldSetUpdatedOnUntilFromCurrentDate() throws IOException {
        // act
        PullRequestsView view = createView();

        // assert
        assertEquals("", view.getUpdatedOnUntil());
    }

    @Test
    void shouldLoadPullRequestsWhenThereIsOnlyOnePageOfResults() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");

        LocalDateTime dt1                            = LocalDateTime.of(2010, Month.JUNE, 1, 0, 0);
        LocalDateTime dt2                            = LocalDateTime.of(2011, Month.JULY, 2, 0, 0);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[] {
            new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
            new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")};

        expectedPullRequestModels[0].setReviewerTeams(new String[] {null, null});
        expectedPullRequestModels[1].setReviewerTeams(new String[] {null, null});

        when(pullRequestClient.loadAllDetails(
                 new PullRequestsRequest(new RepositoryDescriptor("currentUser", "repo"),
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
    void shouldUseTeamMapperToAssignTeams() throws IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");

        LocalDateTime dt1                            = LocalDateTime.of(2010, Month.JUNE, 1, 0, 0);
        LocalDateTime dt2                            = LocalDateTime.of(2011, Month.JULY, 2, 0, 0);
        PullRequestModel[] expectedPullRequestModels = new PullRequestModel[] {
            new PullRequestModel(1, "description 1", "OPEN", dt1, dt1, "mfrauenholtz", null, null),
            new PullRequestModel(2, "description 2", "OPEN", dt2, dt2, "ngeor", "ngeor", "reviewer 1")};

        expectedPullRequestModels[0].setAuthorTeam("team1");
        expectedPullRequestModels[0].setReviewerTeams(new String[] {null, null});
        expectedPullRequestModels[1].setReviewerTeams(new String[] {null, "team2"});

        when(teamMapper.userToTeam("mfrauenholtz")).thenReturn("team1");
        when(teamMapper.userToTeam("reviewer 1")).thenReturn("team2");

        when(pullRequestClient.loadAllDetails(
                 new PullRequestsRequest(new RepositoryDescriptor("currentUser", "repo"),
                                         PullRequestsRequest.State.Merged,
                                         new LocalDateInterval(DateHelper.localDate(2016, 5, 5), null))))
            .thenReturn(Arrays.asList(firstWithParticipants, secondWithParticipants));

        // act
        PullRequestsView view = createView();

        // assert
        PullRequestModel[] pullRequestModels = view.getPullRequests().toArray();
        assertArrayEquals(expectedPullRequestModels, pullRequestModels);
    }

    private DemoControllerImpl createDefaultDemoController() {
        return new DemoControllerImpl(pullRequestClient, teamMapper);
    }

    private PullRequestsView createView() throws IOException {
        return createDefaultDemoController().createView(req);
    }
}
