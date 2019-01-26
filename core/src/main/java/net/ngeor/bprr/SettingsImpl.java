package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.ngeor.util.ResourceLoader;

/**
 * Implementation of settings.
 */
class SettingsImpl implements Settings {
    private final String user;
    private final String secret;

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
        this.user   = properties.getProperty("user");
        this.secret = properties.getProperty("secret");
    }

    SettingsImpl(String user, String secret) {
        this.user   = user;
        this.secret = secret;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getSecret() {
        return secret;
    }
}
