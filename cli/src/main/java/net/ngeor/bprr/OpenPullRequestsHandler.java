package net.ngeor.bprr;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;

/**
 * Handles open pull requests.
 */
public class OpenPullRequestsHandler {
    /**
     * Handles the command.
     * @param pullRequestClient
     * @param programOptions
     * @param out
     * @param teamMapper
     * @throws IOException
     */
    public void
    handle(PullRequestClient pullRequestClient, ProgramOptions programOptions, PrintStream out, TeamMapper teamMapper)
        throws IOException {
        RepositoryDescriptor repositoryDescriptor =
            new RepositoryDescriptor(programOptions.getOwner(), programOptions.getRepository());
        PullRequestsRequest request = new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Open);

        boolean isGroupByTeam = programOptions.isGroupByTeam();
        if (!isGroupByTeam) {
            PullRequests pullRequests = pullRequestClient.load(request);
            out.println(pullRequests.getSize());
        } else {
            Statistics statistics          = new Statistics();
            List<PullRequest> pullRequests = pullRequestClient.loadAllDetails(request);
            List<Statistic> statisticList  = statistics.countByAuthorTeam(pullRequests, teamMapper);
            for (Statistic statistic : statisticList) {
                out.println(String.format("%s %d", statistic.getUsername(), statistic.getCount()));
            }
        }
    }
}
