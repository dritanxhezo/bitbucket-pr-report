package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequest;
import net.ngeor.bprr.serialization.PullRequests;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class OpenPullRequestsHandler {
    public void handle(PullRequestClient pullRequestClient, ProgramOptions programOptions, PrintStream out, TeamMapper teamMapper) throws IOException {
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor(programOptions.getUser(), programOptions.getRepository());
        PullRequestsRequest request = new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Open);

        String team = programOptions.getTeam();
        if (StringUtils.isBlank(team)) {
            PullRequests pullRequests = pullRequestClient.load(request);
            out.println(pullRequests.getSize());
        } else {
            List<PullRequest> pullRequests = pullRequestClient.loadAllDetails(request);
            int count = 0;
            for (PullRequest pullRequest : pullRequests) {
                String username = pullRequest.getAuthor().getUsername();
                String pullRequestTeam = teamMapper.userToTeam(username);
                if (team.equalsIgnoreCase(pullRequestTeam)) {
                    count++;
                }
            }

            out.println(count);
        }
    }
}
