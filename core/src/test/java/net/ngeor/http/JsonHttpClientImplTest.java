package net.ngeor.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.util.ResourceLoaderImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link JsonHttpClientImpl}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class JsonHttpClientImplTest {

    /**
     * Unit tests for pull requests.
     */
    @Nested
    class PullRequests {
        private net.ngeor.bitbucket.PullRequests response;

        @BeforeEach
        void before() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream =
                new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bitbucket/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests");
            HttpClient simpleHttpClient    = setupHttpClientFactory(responseStream, expected);
            JsonHttpClient bitbucketClient = new JsonHttpClientImpl(simpleHttpClient);

            // act
            response = bitbucketClient.read("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests",
                                            net.ngeor.bitbucket.PullRequests.class);
        }

        @Test
        void shouldHaveResponse() {
            assertNotNull(response);
        }

        @Test
        void shouldHaveCorrectPage() {
            assertEquals(1, response.getPage());
        }

        @Test
        void shouldHaveValues() {
            List<PullRequest> values = response.getValues();
            assertNotNull(values);
        }

        @Test
        void shouldHaveCorrectId() {
            assertEquals(3767, response.getValues().get(0).getId());
        }

        @Test
        void shouldHaveCorrectState() {
            assertEquals("OPEN", response.getValues().get(0).getState());
        }

        @Test
        void shouldHaveCorrectCreatedOn() {
            LocalDateTime createdOn = response.getValues().get(0).getCreatedOn();
            LocalDateTime expected  = LocalDateTime.of(2013, Month.NOVEMBER, 5, 23, 59, 26, 480984000);
            assertEquals(expected, createdOn);
        }

        @Test
        void shouldHaveLinkToSelf() {
            assertEquals("https://api.bitbucket.org/2.0/repositories/bitbucket/bitbucket/pullrequests/3767",
                         response.getValues().get(0).getLinks().getSelf().getHref());
        }
    }

    private static HttpClient setupHttpClientFactory(InputStream responseStream, URI expectedURI) throws IOException {
        assertThat(responseStream).as("response stream").isNotNull();
        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.read(expectedURI.toString())).thenReturn(responseStream);
        return httpClient;
    }
}
