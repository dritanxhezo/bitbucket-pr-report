package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class SettingsImpl implements Settings {
    private final String user;
    private final String secret;

    public SettingsImpl(ResourceLoader resourceLoader) throws IOException {
        InputStream in = resourceLoader.getResourceAsStream("net/ngeor/bprr/bitbucket.properties");
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        this.user = properties.getProperty("user");
        this.secret = properties.getProperty("secret");
    }

    public SettingsImpl(String user, String secret) {
        this.user = user;
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
