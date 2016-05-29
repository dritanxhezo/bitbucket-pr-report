package net.ngeor.bprr;

import net.ngeor.bprr.serialization.Author;
import net.ngeor.bprr.serialization.PullRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatisticsTest {
    @Test
    public void shouldCreateReportPerAuthor() {
        List<PullRequest> pullRequests = Arrays.asList(
                mockPullRequest("ngeor"),
                mockPullRequest("ngeor"),
                mockPullRequest("test")
        );

        Statistics statistics = new Statistics();

        // act
        List<Statistic> result = statistics.countByAuthor(pullRequests);

        // assert
        Statistic[] expected = new Statistic[] {
                new Statistic("ngeor", 2),
                new Statistic("test", 1)
        };

        assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void shouldCreateReportPerAuthorTeam() {
        List<PullRequest> pullRequests = Arrays.asList(
                mockPullRequest("ngeor"),
                mockPullRequest("ngeor"),
                mockPullRequest("test1"),
                mockPullRequest("test2")
        );

        Statistics statistics = new Statistics();
        TeamMapper teamMapper = mock(TeamMapper.class);
        when(teamMapper.userToTeam("ngeor")).thenReturn("team1");
        when(teamMapper.userToTeam("test1")).thenReturn("team1");
        when(teamMapper.userToTeam("test2")).thenReturn("team2");

        // act
        List<Statistic> result = statistics.countByAuthorTeam(pullRequests, teamMapper);

        // assert
        Statistic[] expected = new Statistic[] {
                new Statistic("team1", 3),
                new Statistic("team2", 1)
        };

        assertArrayEquals(expected, result.toArray());
    }

    private static PullRequest mockPullRequest(String authorUsername) {
        PullRequest pullRequest = mock(PullRequest.class);
        Author author = mock(Author.class);

        when(pullRequest.getAuthor()).thenReturn(author);
        when(author.getUsername()).thenReturn(authorUsername);

        return pullRequest;
    }
}
