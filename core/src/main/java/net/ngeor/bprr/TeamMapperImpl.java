package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.ngeor.util.ResourceLoader;

/**
 * Implementation of team mapping.
 */
public class TeamMapperImpl implements TeamMapper {
    private final ResourceLoader resourceLoader;
    private final Map<String, String> userToTeam = new HashMap<>();

    public TeamMapperImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private void put(String user, String team) {
        userToTeam.put(user, team);
    }

    /**
     * Implementation of team mapper.
     */
    public void loadFromProperties() throws IOException {
        if (!userToTeam.isEmpty()) {
            return;
        }

        Properties properties = new Properties();
        populateProperties(properties);

        for (String username : properties.stringPropertyNames()) {
            put(username, properties.getProperty(username));
        }
    }

    private void populateProperties(Properties properties) throws IOException {
        try (InputStream in = resourceLoader.getResourceAsStream("net/ngeor/bprr/teams.properties")) {
            if (in == null) {
                return;
            }

            properties.load(in);
        }
    }

    @Override
    public String userToTeam(String user) {
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
