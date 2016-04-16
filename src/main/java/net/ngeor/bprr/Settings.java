package net.ngeor.bprr;

public class Settings {
    private String user;
    private String clientId;
    private String secret;

    private Settings(String user, String clientId, String secret) {
        this.user = user;
        this.clientId = clientId;
        this.secret = secret;
    }

    private static final Settings instance = new Settings(
            // TODO move to properties file
            "user",
            "key",
            "secret"
    );

    public static Settings getInstance() {
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
