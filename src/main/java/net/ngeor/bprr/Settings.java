package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private String user;
    private String clientId;
    private String secret;

    private Settings(String user, String clientId, String secret) {
        this.user = user;
        this.clientId = clientId;
        this.secret = secret;
    }

    private static Settings load() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream("net/ngeor/bprr/bitbucket.properties");
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        return new Settings(
                properties.getProperty("user"),
                properties.getProperty("key"),
                properties.getProperty("secret"));
    }

    private static Settings instance;

    public static Settings getInstance() throws IOException {
        if (instance == null) {
            instance = load();
        }

        return instance;
    }

    public String getUser() {
        return user;
    }

    public String getClientId() {
        return clientId;
    }


    public String getSecret() {
        return secret;
    }
}
