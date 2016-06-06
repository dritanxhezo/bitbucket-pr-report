package net.ngeor.bprr;

import net.ngeor.bprr.serialization.PullRequest;
import net.ngeor.testutil.HttpGetMatcher;
import net.ngeor.util.ResourceLoaderImpl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(Enclosed.class)
public class BitbucketClientImplTest {
    public static class PullRequests {
        private net.ngeor.bprr.serialization.PullRequests response;

        @Before
        public void before() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream = new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bprr/serialization/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests");
            HttpClientFactory httpClientFactory = setupHttpClientFactory(responseStream, expected);
            BitbucketClientImpl bitbucketClient = new BitbucketClientImpl(httpClientFactory, "some secret");

            // act
            response = bitbucketClient.execute(
                    "repositories/owner/repo_slug/pullrequests",
                    net.ngeor.bprr.serialization.PullRequests.class);
        }

        @Test
        public void shouldHaveResponse() {
            assertNotNull(response);
        }

        @Test
        public void shouldHaveCorrectPage() {
            assertEquals(1, response.getPage());
        }

        @Test
        public void shouldHaveValues() {
            PullRequest[] values = response.getValues();
            assertNotNull(values);
        }

        @Test
        public void shouldHaveCorrectId() {
            assertEquals(3767, response.getValues()[0].getId());
        }

        @Test
        public void shouldHaveCorrectState() {
            assertEquals("OPEN", response.getValues()[0].getState());
        }

        @Test
        public void shouldHaveCorrectCreatedOn() {
            Date createdOn = response.getValues()[0].getCreatedOn();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.set(2013, Calendar.NOVEMBER, 5, 23, 59, 26);
            calendar.set(Calendar.MILLISECOND, 480);
            Date expected = calendar.getTime();
            assertEquals(expected, createdOn);
        }

        @Test
        public void shouldHaveLinkToSelf() {
            assertEquals("https://api.bitbucket.org/2.0/repositories/bitbucket/bitbucket/pullrequests/3767", response.getValues()[0].getLinks().getSelf().getHref());
        }
    }

    public static class URLHandling {
        @Test
        public void shouldUseRequestInTheURI() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream = new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bprr/serialization/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests");
            HttpClientFactory httpClientFactory = setupHttpClientFactory(responseStream, expected);
            BitbucketClientImpl bitbucketClient = new BitbucketClientImpl(httpClientFactory, "some secret");

            // act
            bitbucketClient.execute(
                    "repositories/owner/repo_slug/pullrequests",
                    net.ngeor.bprr.serialization.PullRequests.class);

            // assert
            HttpClient httpClient = httpClientFactory.create();
            verify(httpClient).execute(matchHttpGet(expected));
        }

        @Test
        public void shouldUseRequestAsIsIfItIsBitbucketUrl() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream = new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bprr/serialization/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/whatever");
            HttpClientFactory httpClientFactory = setupHttpClientFactory(responseStream, expected);
            BitbucketClientImpl bitbucketClient = new BitbucketClientImpl(httpClientFactory, "some secret");

            // act
            bitbucketClient.execute(
                    "https://api.bitbucket.org/2.0/whatever",
                    net.ngeor.bprr.serialization.PullRequests.class);

            // assert
            HttpClient httpClient = httpClientFactory.create();
            verify(httpClient).execute(matchHttpGet(expected));
        }
    }

    private static HttpClientFactory setupHttpClientFactory(InputStream responseStream, URI expectedURI) throws IOException {
        assertNotNull("null response stream!", responseStream);
        HttpEntity httpEntity = mock(HttpEntity.class);
        final HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(responseStream);
        when(httpClient.execute(matchHttpGet(expectedURI))).thenReturn(httpResponse);
        HttpClientFactory httpClientFactory = new HttpClientFactory() {
            @Override
            public HttpClient create() {
                return httpClient;
            }
        };
        return httpClientFactory;
    }

    private static HttpUriRequest matchHttpGet(URI expectedURI) {
        return Matchers.argThat(new HttpGetMatcher(expectedURI));
    }

}
