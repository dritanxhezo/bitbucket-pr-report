package net.ngeor.bprr;

import net.ngeor.bitbucket.PullRequest;
import net.ngeor.util.ResourceLoaderImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class BitbucketClientImplTest {
    public static class PullRequests {
        private net.ngeor.bitbucket.PullRequests response;

        @Before
        public void before() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream = new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bitbucket/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests");
            SimpleHttpClient simpleHttpClient = setupHttpClientFactory(responseStream, expected);
            BitbucketClientImpl bitbucketClient = new BitbucketClientImpl(simpleHttpClient, "some secret");

            // act
            response = bitbucketClient.execute(
                    "repositories/owner/repo_slug/pullrequests",
                    net.ngeor.bitbucket.PullRequests.class);
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
            InputStream responseStream = new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bitbucket/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests");
            SimpleHttpClient simpleHttpClient = setupHttpClientFactory(responseStream, expected);
            BitbucketClientImpl bitbucketClient = new BitbucketClientImpl(simpleHttpClient, "some secret");

            // act
            net.ngeor.bitbucket.PullRequests pullRequests = bitbucketClient.execute(
                    "repositories/owner/repo_slug/pullrequests",
                    net.ngeor.bitbucket.PullRequests.class);

            // assert
            assertEquals(12, pullRequests.getSize());
        }

        @Test
        public void shouldUseRequestAsIsIfItIsBitbucketUrl() throws URISyntaxException, IOException {
            // arrange
            InputStream responseStream = new ResourceLoaderImpl().getResourceAsStream("net/ngeor/bitbucket/PullRequestsSimple.json");
            URI expected = new URI("https://api.bitbucket.org/2.0/whatever");
            SimpleHttpClient simpleHttpClient = setupHttpClientFactory(responseStream, expected);
            BitbucketClientImpl bitbucketClient = new BitbucketClientImpl(simpleHttpClient, "some secret");

            // act
            net.ngeor.bitbucket.PullRequests pullRequests = bitbucketClient.execute(
                    "https://api.bitbucket.org/2.0/whatever",
                    net.ngeor.bitbucket.PullRequests.class);

            // assert
            assertEquals(12, pullRequests.getSize());
        }
    }

    private static SimpleHttpClient setupHttpClientFactory(final InputStream responseStream, final URI expectedURI) throws IOException {
        assertNotNull("null response stream!", responseStream);
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient() {
            @Override
            public <E> E load(String url, String basicAuthenticationHeader, InputStreamClient<E> inputStreamClient) throws IOException {
                assertEquals(url, expectedURI.toString());
                assertEquals(basicAuthenticationHeader, "some secret");
                return inputStreamClient.consume(responseStream);
            }
        };
        return simpleHttpClient;
    }
}
