package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class SettingsImpl implements Settings {
    private String user;
    private String clientId;
    private String secret;

    public SettingsImpl(ResourceLoader resourceLoader) throws IOException {
        InputStream in = resourceLoader.getResourceAsStream("net/ngeor/bprr/bitbucket.properties");
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        this.user = properties.getProperty("user");
        this.clientId = properties.getProperty("key");
        this.secret = properties.getProperty("secret");
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getSecret() {
        return secret;
    }
}
