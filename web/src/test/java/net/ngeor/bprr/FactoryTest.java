package net.ngeor.bprr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ngeor.http.HttpClient;
import net.ngeor.http.HttpClientImpl;
import net.ngeor.http.JsonHttpClient;
import net.ngeor.http.JsonHttpClientImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link Factory}.
 */
@ExtendWith(MockitoExtension.class)
class FactoryTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    void beforeEach() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void httpClient() {
        // arrange
        doReturn("spider").when(session).getAttribute("username");
        doReturn("secret").when(session).getAttribute("password");

        // act
        HttpClient httpClient = Factory.INSTANCE.httpClient(request);

        // assert
        assertThat(httpClient).isEqualTo(new HttpClientImpl("spider", "secret"));
    }

    @Test
    void jsonHttpClient() {
        // arrange
        doReturn("spider").when(session).getAttribute("username");
        doReturn("secret").when(session).getAttribute("password");

        // act
        JsonHttpClient jsonHttpClient = Factory.INSTANCE.jsonHttpClient(request);

        // assert
        assertThat(jsonHttpClient).isEqualTo(new JsonHttpClientImpl(new HttpClientImpl("spider", "secret")));
    }

    @Test
    void bitbucketClient() {
        // arrange
        doReturn("spider").when(session).getAttribute("username");
        doReturn("secret").when(session).getAttribute("password");
        doReturn("acme").when(session).getAttribute("owner");

        // act
        BitbucketClient bitbucketClient = Factory.INSTANCE.bitbucketClient(request);

        // assert
        assertThat(bitbucketClient)
            .isEqualTo(new BitbucketClient(new JsonHttpClientImpl(new HttpClientImpl("spider", "secret")), "acme"));
    }
}
