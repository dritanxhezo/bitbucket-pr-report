package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequest;
import net.ngeor.bprr.serialization.PullRequests;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class OpenPullRequestsHandler {
    public void handle(PullRequestClient pullRequestClient, ProgramOptions programOptions, PrintStream out, TeamMapper teamMapper) throws IOException {
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor(programOptions.getUser(), programOptions.getRepository());
        PullRequestsRequest request = new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Open);

        boolean isGroupByTeam = programOptions.isGroupByTeam();
        if (!isGroupByTeam) {
            PullRequests pullRequests = pullRequestClient.load(request);
            out.println(pullRequests.getSize());
        } else {
            Statistics statistics = new Statistics();
            List<PullRequest> pullRequests = pullRequestClient.loadAllDetails(request);
            List<Statistic> statisticList = statistics.countByAuthorTeam(pullRequests, teamMapper);
            for (Statistic statistic : statisticList) {
                out.println(String.format("%s %d", statistic.getUsername(), statistic.getCount()));
            }
        }
    }
}
