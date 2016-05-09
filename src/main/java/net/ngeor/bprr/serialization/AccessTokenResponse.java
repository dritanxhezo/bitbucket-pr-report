package net.ngeor.bprr.serialization;

/**
 * The JSON response after requesting an OAuth access token.
 */
public class AccessTokenResponse {
    private String access_token;
    private String scopes;
    private int expires_in;
    private String refresh_token;
    private String token_type;

    public String getAccessToken() {
        return access_token;
    }

    public String getScopes() {
        return scopes;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getTokenType() {
        return token_type;
    }
}
