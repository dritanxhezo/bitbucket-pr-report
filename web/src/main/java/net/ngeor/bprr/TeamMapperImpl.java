package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

class TeamMapperImpl implements TeamMapper {
    private final ResourceLoader resourceLoader;
    private HashMap<String, String> userToTeam = new HashMap<>();

    public TeamMapperImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void assignTeams(PullRequestModel model) {
        model.setAuthorTeam(mapAuthor(model.getAuthor()));
        String[] reviewers = model.getReviewers();
        if (reviewers != null) {
            model.setReviewerTeams(mapAuthors(reviewers));
        }
    }

    private String[] mapAuthors(String... reviewers) {
        String[] result = new String[reviewers.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = mapAuthor(reviewers[i]);
        }

        return result;
    }

    void put(String user, String team) {
        userToTeam.put(user, team);
    }

    public void loadFromProperties() {
        if (!userToTeam.isEmpty()) {
            return;
        }

        Properties properties = new Properties();
        populateProperties(properties);

        for (String username : properties.stringPropertyNames()) {
            put(username, properties.getProperty(username));
        }
    }

    private void populateProperties(Properties properties) {
        InputStream in = resourceLoader.getResourceAsStream("net/ngeor/bprr/teams.properties");

        try {
            properties.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private String mapAuthor(String user) {
        if (user == null) {
            return null;
        }

        String team = userToTeam.get(user);
        if (team == null) {
            throw new IllegalArgumentException("Unknown team for user " + user);
        }

        return team;
    }
}