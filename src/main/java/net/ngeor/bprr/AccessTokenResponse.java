package net.ngeor.bprr;

import com.google.gson.Gson;

import java.io.Reader;

/**
 * The JSON response after requesting an OAuth access token.
 */
public class AccessTokenResponse {
    private String access_token;
    private String scopes;
    private int expires_in;
    private String refresh_token;
    private String token_type;

    public static AccessTokenResponse parse(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AccessTokenResponse.class);
    }

    public static AccessTokenResponse parse(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader, AccessTokenResponse.class);
    }

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
