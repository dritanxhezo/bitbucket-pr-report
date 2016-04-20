package net.ngeor.bprr;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class AccessTokenResponseFactoryTest {
    @Test
    public void shouldParseJson() {
        String json = "{\"access_token\":\"lalala-nZSxalZj3wdG9WjE=\"," +
                "\"scopes\":\"snippet issue pullrequest project team account\"," +
                "\"expires_in\":3600," +
                "\"refresh_token\":\"bar\"," +
                "\"token_type\":\"bearer\"}\n";
        AccessTokenResponseFactory factory = new AccessTokenResponseFactory();
        StringReader reader = new StringReader(json);
        AccessTokenResponse response = factory.parse(reader);
        Assert.assertEquals("access_token", "lalala-nZSxalZj3wdG9WjE=", response.getAccessToken());
        Assert.assertEquals("scopes", "snippet issue pullrequest project team account", response.getScopes());
        Assert.assertEquals("expires_in", 3600, response.getExpiresIn());
        Assert.assertEquals("refresh_token", "bar", response.getRefreshToken());
        Assert.assertEquals("token_type", "bearer", response.getTokenType());
    }
}
