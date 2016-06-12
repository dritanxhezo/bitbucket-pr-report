package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequest;
import net.ngeor.bprr.serialization.PullRequests;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class OpenPullRequestsHandlerTest {
    @Test
    public void shouldFetchOpenPullRequests() throws IOException {
        PullRequestClient pullRequestClient = mock(PullRequestClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out = mock(PrintStream.class);
        when(programOptions.getRepository()).thenReturn("repository");
        when(programOptions.getUser()).thenReturn("user");
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor("user", "repository");
        PullRequestsRequest pullRequestsRequest = new PullRequestsRequest(
                repositoryDescriptor,
                PullRequestsRequest.State.Open);
        PullRequests response = mock(PullRequests.class);
        when(response.getSize()).thenReturn(5);
        when(pullRequestClient.load(pullRequestsRequest)).thenReturn(response);

        // act
        OpenPullRequestsHandler handler = new OpenPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, out);

        // assert
        verify(out).println(5);
    }

    @Test
    public void shouldFetchOpenPullRequestsForTeam() throws IOException {
        PullRequestClient pullRequestClient = mock(PullRequestClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out = mock(PrintStream.class);
        when(programOptions.getRepository()).thenReturn("repository");
        when(programOptions.getUser()).thenReturn("user");
        when(programOptions.getTeam()).thenReturn("team");
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor("user", "repository");
        PullRequestsRequest pullRequestsRequest = new PullRequestsRequest(
                repositoryDescriptor,
                PullRequestsRequest.State.Open);

        List<PullRequest> pullRequests = Arrays.asList(new PullRequest(), new PullRequest(), new PullRequest());
        when(pullRequestClient.loadAllDetails(pullRequestsRequest)).thenReturn(pullRequests);

        // act
        OpenPullRequestsHandler handler = new OpenPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, out);

        // assert
        verify(out).println(3);
    }

}
