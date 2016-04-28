package net.ngeor.bprr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class DefaultBitbucketClientTest {
    public static class PullRequests {
        private PullRequestsResponse response;

        @Before
        public void before() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream = getClass().getResourceAsStream("pullRequests.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests?access_token=123");
            HttpClientFactory httpClientFactory = setupHttpClientFactory(responseStream, expected);
            DefaultBitbucketClient bitbucketClient = new DefaultBitbucketClient(httpClientFactory);
            bitbucketClient.setAccessToken("123");

            // act
            response = bitbucketClient.execute(
                    "repositories/owner/repo_slug/pullrequests",
                    PullRequestsResponse.class);
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
            PullRequestResponse[] values = response.getValues();
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
            calendar.set(2013, 10 /* zero based month index */, 05, 23, 59, 26);
            calendar.set(Calendar.MILLISECOND, 480);
            Date expected = calendar.getTime();
            assertEquals(expected, createdOn);
        }
    }

    private static HttpClientFactory setupHttpClientFactory(InputStream responseStream, URI expectedURI) throws IOException {
        assertNotNull("null response stream!", responseStream);
        HttpEntity httpEntity = mock(HttpEntity.class);
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(responseStream);
        when(httpClient.execute(Matchers.argThat(new ArgumentMatcher<HttpUriRequest>() {

            @Override
            public boolean matches(Object o) {
                Assert.assertThat(o, instanceOf(HttpGet.class));
                HttpGet httpGet = (HttpGet) o;
                assertEquals(expectedURI, httpGet.getURI());
                return true;
            }
        }))).thenReturn(httpResponse);
        HttpClientFactory httpClientFactory = () -> httpClient;
        return httpClientFactory;
    }
}
