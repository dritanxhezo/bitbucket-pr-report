package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class TeamMapperImpl implements TeamMapper {
    private final ResourceLoader resourceLoader;
    private final HashMap<String, String> userToTeam = new HashMap<>();

    public TeamMapperImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private void put(String user, String team) {
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
        if (in == null) {
            // TODO: log warning?
            return;
        }

        try {
            properties.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(in);
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
