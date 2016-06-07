package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequests;
import net.ngeor.util.DateHelper;
import org.joda.time.Interval;

import java.io.IOException;
import java.io.PrintStream;

public class MergedPullRequestsHandler {
    public void handle(PullRequestClient pullRequestClient, ProgramOptions programOptions, PrintStream out) throws IOException {
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor(programOptions.getUser(), programOptions.getRepository());
        PullRequestsRequest request = new PullRequestsRequest(
                repositoryDescriptor,
                PullRequestsRequest.State.Merged,
                new Interval(DateHelper.utcToday().minusDays(1), DateHelper.utcToday()));
        PullRequests pullRequests = pullRequestClient.load(request);

        // generate output that can be used with zabbix_sender
        String zabbixHost = programOptions.getZabbixHost();
        String zabbixKey = programOptions.getZabbixKey();
        out.println(zabbixHost + " " + zabbixKey + " " + pullRequests.getSize());
    }
}
