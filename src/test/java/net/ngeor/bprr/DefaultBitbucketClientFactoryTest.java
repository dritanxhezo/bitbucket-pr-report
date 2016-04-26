package net.ngeor.bprr;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultBitbucketClientFactoryTest {
    @Test
    public void shouldGetAccessTokenFromSession() {
        BitbucketClientFactory factory = new DefaultBitbucketClientFactory();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("accessToken")).thenReturn("pass123");

        // act
        BitbucketClient client = factory.createClient(request);

        // assert
        assertEquals("pass123", client.getAccessToken());
    }
}
