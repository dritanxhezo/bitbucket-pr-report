package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequest;
import net.ngeor.bprr.serialization.PullRequests;
import net.ngeor.util.DateHelper;
import org.joda.time.Interval;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MergedPullRequestsHandlerTest {
    @Test
    public void shouldFetchPullRequestsFromYesterday() throws IOException {
        PullRequestClient pullRequestClient = mock(PullRequestClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out = mock(PrintStream.class);
        when(programOptions.getRepository()).thenReturn("repository");
        when(programOptions.getUser()).thenReturn("user");
        when(programOptions.getZabbixKey()).thenReturn("key");
        when(programOptions.getZabbixHost()).thenReturn("host");
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor("user", "repository");
        PullRequestsRequest pullRequestsRequest = new PullRequestsRequest(
                repositoryDescriptor,
                PullRequestsRequest.State.Merged,
                new Interval(DateHelper.utcToday().minusDays(1), DateHelper.utcToday()));
        PullRequests response = mock(PullRequests.class);
        when(response.getSize()).thenReturn(5);
        when(pullRequestClient.load(pullRequestsRequest)).thenReturn(response);

        // act
        MergedPullRequestsHandler handler = new MergedPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, out);

        // assert
        verify(out).println("host key 5");
    }
}
