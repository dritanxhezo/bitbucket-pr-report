package net.ngeor.bprr;

import java.io.IOException;
import java.io.PrintStream;

import net.ngeor.bitbucket.PullRequests;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;

/**
 * Handles the merged pull requests command.
 */
public class MergedPullRequestsHandler {
    /**
     * Handles the command.
     * @param pullRequestClient
     * @param programOptions
     * @param out
     * @throws IOException
     */
    public void handle(PullRequestClient pullRequestClient, ProgramOptions programOptions, PrintStream out)
        throws IOException {
        RepositoryDescriptor repositoryDescriptor =
            new RepositoryDescriptor(programOptions.getOwner(), programOptions.getRepository());
        PullRequestsRequest request = new PullRequestsRequest(
            repositoryDescriptor,
            PullRequestsRequest.State.Merged,
            new LocalDateInterval(DateHelper.utcToday().minusDays(programOptions.getStartDaysDiff()),
                                  DateHelper.utcToday().plusDays(1)));
        PullRequests pullRequests = pullRequestClient.load(request);

        out.println(pullRequests.getSize());
    }
}
