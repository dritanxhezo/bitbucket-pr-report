package net.ngeor.bprr;

import net.ngeor.bprr.serialization.PullRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    // TODO support teams
    public List<Statistic> countByAuthor(List<PullRequest> pullRequests) {
        List<Statistic> result = new ArrayList<>();
        Map<String, List<PullRequest>> groupedByAuthor = groupByAuthor(pullRequests);
        for (Map.Entry<String, List<PullRequest>> mapEntry : groupedByAuthor.entrySet()) {
            String username = mapEntry.getKey();
            List<PullRequest> pullRequestsOfAuthor = mapEntry.getValue();
            Statistic statistic = new Statistic(username, pullRequestsOfAuthor.size());
            result.add(statistic);
        }

        return result;
    }

    private Map<String, List<PullRequest>> groupByAuthor(List<PullRequest> pullRequests) {
        Map<String, List<PullRequest>> result = new HashMap<>();
        for (PullRequest pullRequest : pullRequests) {
            String username = pullRequest.getAuthor().getUsername();
            List<PullRequest> pullRequestsOfAuthor;
            if (result.containsKey(username)) {
                pullRequestsOfAuthor = result.get(username);
            } else {
                pullRequestsOfAuthor = new ArrayList<>();
                result.put(username, pullRequestsOfAuthor);
            }

            pullRequestsOfAuthor.add(pullRequest);
        }

        return result;
    }
}
