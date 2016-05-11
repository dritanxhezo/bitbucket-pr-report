package net.ngeor.bprr;

import java.util.Arrays;
import java.util.HashMap;

public interface TeamMapper {
    void assignTeams(PullRequestModel model);
    void put(String user, String team);
}

class DefaultTeamMapper implements TeamMapper {
    private HashMap<String, String> userToTeam = new HashMap<>();

    public void assignTeams(PullRequestModel model) {
        model.setAuthorTeam(mapAuthor(model.getAuthor()));
        String[] reviewers = model.getReviewers();
        if (reviewers != null) {
            model.setReviewerTeams(
                Arrays.stream(reviewers)
                        .map(u -> mapAuthor(u))
                        .toArray(String[]::new));
        }
    }

    public void put(String user, String team) {
        userToTeam.put(user, team);
    }

    private String mapAuthor(String user) {
        return userToTeam.get(user);
    }
}
