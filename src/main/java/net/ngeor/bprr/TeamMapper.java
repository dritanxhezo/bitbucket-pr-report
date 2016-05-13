package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public interface TeamMapper {
    void assignTeams(PullRequestModel model);
    void put(String user, String team);
    void loadFromProperties() throws IOException;
}

class DefaultTeamMapper implements TeamMapper {
    private HashMap<String, String> userToTeam = new HashMap<>();

    @Override
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

    @Override
    public void put(String user, String team) {
        userToTeam.put(user, team);
    }

    @Override
    public void loadFromProperties() throws IOException {
        if (!userToTeam.isEmpty()) {
            return;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream("net/ngeor/bprr/teams.properties");
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        for (String username : properties.stringPropertyNames()) {
            put(username, properties.getProperty(username));
        }
    }

    private String mapAuthor(String user) {
        return userToTeam.get(user);
    }
}
