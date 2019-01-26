package net.ngeor.bprr;

import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import net.ngeor.bitbucket.PullRequests;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link MergedPullRequestsHandler}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class MergedPullRequestsHandlerTest {
    @Test
    void shouldFetchPullRequestsFromYesterday() throws IOException {
        PullRequestClient pullRequestClient = mock(PullRequestClient.class);
        ProgramOptions programOptions       = mock(ProgramOptions.class);
        PrintStream out                     = mock(PrintStream.class);
        when(programOptions.getRepository()).thenReturn("repository");
        when(programOptions.getOwner()).thenReturn("user");
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor("user", "repository");
        PullRequestsRequest pullRequestsRequest =
            new PullRequestsRequest(repositoryDescriptor,
                                    PullRequestsRequest.State.Merged,
                                    new LocalDateInterval(DateHelper.utcToday(), DateHelper.utcToday().plusDays(1)));
        PullRequests response = mock(PullRequests.class);
        when(response.getSize()).thenReturn(5);
        when(pullRequestClient.load(pullRequestsRequest)).thenReturn(response);

        // act
        MergedPullRequestsHandler handler = new MergedPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, out);

        // assert
        verify(out).println(5);
    }

    @Test
    void shouldUseStartDaysDiffParameter() throws IOException {
        PullRequestClient pullRequestClient = mock(PullRequestClient.class);
        ProgramOptions programOptions       = mock(ProgramOptions.class);
        PrintStream out                     = mock(PrintStream.class);
        when(programOptions.getRepository()).thenReturn("repository");
        when(programOptions.getOwner()).thenReturn("user");
        when(programOptions.getStartDaysDiff()).thenReturn(14);
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor("user", "repository");
        PullRequestsRequest pullRequestsRequest   = new PullRequestsRequest(
            repositoryDescriptor,
            PullRequestsRequest.State.Merged,
            new LocalDateInterval(DateHelper.utcToday().minusDays(14), DateHelper.utcToday().plusDays(1)));
        PullRequests response = mock(PullRequests.class);
        when(response.getSize()).thenReturn(17);
        when(pullRequestClient.load(pullRequestsRequest)).thenReturn(response);

        // act
        MergedPullRequestsHandler handler = new MergedPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, out);

        // assert
        verify(out).println(17);
    }
}
