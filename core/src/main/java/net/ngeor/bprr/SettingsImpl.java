package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.ngeor.util.ResourceLoader;

/**
 * Implementation of settings.
 */
class SettingsImpl implements Settings {
    private final String owner;
    private final String username;
    private final String password;

    /**
     * Creates an instance of this class.
     * @param resourceLoader
     * @throws IOException
     */
    SettingsImpl(ResourceLoader resourceLoader) throws IOException {
        InputStream in        = resourceLoader.getResourceAsStream("net/ngeor/bprr/bitbucket.properties");
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        this.owner    = properties.getProperty("owner");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

    /**
     * Creates an instance of this class.
     * @param owner
     * @param username
     * @param password
     */
    SettingsImpl(String owner, String username, String password) {
        this.owner    = owner;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
