package net.ngeor.bprr;

import net.ngeor.bitbucket.PullRequests;
import net.ngeor.bitbucket.PullRequestsRequest;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;

import java.io.IOException;
import java.io.PrintStream;

public class MergedPullRequestsHandler {
    public void handle(PullRequestClient pullRequestClient, ProgramOptions programOptions, PrintStream out) throws IOException {
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor(programOptions.getUser(), programOptions.getRepository());
        PullRequestsRequest request = new PullRequestsRequest(
                repositoryDescriptor,
                PullRequestsRequest.State.Merged,
                new LocalDateInterval(DateHelper.utcToday(), DateHelper.utcToday().plusDays(1)));
        PullRequests pullRequests = pullRequestClient.load(request);

        out.println(pullRequests.getSize());
    }
}
