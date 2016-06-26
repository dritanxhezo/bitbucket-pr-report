package net.ngeor.bprr;

import net.ngeor.bitbucket.PullRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Statistics {
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

    public List<Statistic> countByAuthorTeam(List<PullRequest> pullRequests, TeamMapper teamMapper) {
        Map<String, List<PullRequest>> userMap = groupByAuthor(pullRequests);
        Map<String, List<PullRequest>> groupMap = groupByTeam(userMap, teamMapper);
        List<Statistic> result = new ArrayList<>();
        for (Map.Entry<String, List<PullRequest>> mapEntry : groupMap.entrySet()) {
            String team = mapEntry.getKey();
            List<PullRequest> pullRequestsOfTeam = mapEntry.getValue();
            Statistic statistic = new Statistic(team, pullRequestsOfTeam.size());
            result.add(statistic);
        }

        return result;
    }

    private Map<String, List<PullRequest>> groupByTeam(Map<String, List<PullRequest>> userMap, TeamMapper teamMapper) {
        Map<String, List<PullRequest>> result = new TreeMap<>();
        for (Map.Entry<String, List<PullRequest>> userEntry : userMap.entrySet()) {
            String username = userEntry.getKey();
            String team = teamMapper.userToTeam(username);
            List<PullRequest> teamPullRequests;
            if (result.containsKey(team)) {
                teamPullRequests = result.get(team);
            } else {
                teamPullRequests = new ArrayList<>();
                result.put(team, teamPullRequests);
            }

            teamPullRequests.addAll(userEntry.getValue());
        }

        return result;
    }

    private Map<String, List<PullRequest>> groupByAuthor(List<PullRequest> pullRequests) {
        Map<String, List<PullRequest>> result = new TreeMap<>();
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
