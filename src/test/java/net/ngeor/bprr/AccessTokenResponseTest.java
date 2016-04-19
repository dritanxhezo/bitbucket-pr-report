package net.ngeor.bprr;

import org.junit.Assert;
import org.junit.Test;

public class AccessTokenResponseTest {
    @Test
    public void shouldParseJson() {
        String json = "{\"access_token\":\"lalala-nZSxalZj3wdG9WjE=\"," +
                "\"scopes\":\"snippet issue pullrequest project team account\"," +
                "\"expires_in\":3600," +
                "\"refresh_token\":\"bar\"," +
                "\"token_type\":\"bearer\"}\n";
        AccessTokenResponse response = AccessTokenResponse.parse(json);
        Assert.assertEquals("access_token", "lalala-nZSxalZj3wdG9WjE=", response.getAccessToken());
        Assert.assertEquals("scopes", "snippet issue pullrequest project team account", response.getScopes());
        Assert.assertEquals("expires_in", 3600, response.getExpiresIn());
        Assert.assertEquals("refresh_token", "bar", response.getRefreshToken());
        Assert.assertEquals("token_type", "bearer", response.getTokenType());
    }
}
