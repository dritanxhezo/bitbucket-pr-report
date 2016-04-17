package net.ngeor.bprr;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OAuthHelperTest {
    @Test
    public void shouldGetAccessTokenFromSession() {
        // arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("accessToken")).thenReturn("123");
        OAuthHelper helper = new OAuthHelper(request);

        // act
        String token = helper.getAccessToken();

        // assert
        Assert.assertEquals("123", token);
    }
}
