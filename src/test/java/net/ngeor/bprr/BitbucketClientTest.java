package net.ngeor.bprr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BitbucketClientTest {
    @Test
    public void shouldGetPullRequests() throws IOException, URISyntaxException {
        // arrange
        InputStream responseStream = getClass().getResourceAsStream("pullRequests.json");
        URI expected = new URI("https://api.bitbucket.org/2.0/repositories/owner/repo_slug/pullrequests?access_token=123");
        HttpClientFactory httpClientFactory = setupHttpClientFactory(responseStream, expected);
        BitbucketClient bitbucketClient = new BitbucketClient();
        bitbucketClient.setResource("repositories/owner/repo_slug/pullrequests");
        bitbucketClient.setAccessToken("123");
        bitbucketClient.setHttpClientFactory(httpClientFactory);

        // act
        PullRequestsResponse response = bitbucketClient.execute(PullRequestsResponse.class);

        // assert
        Assert.assertNotNull(response);
        Assert.assertEquals(1, response.getPage());
        Assert.assertEquals(3767, response.getValues()[0].getId());
    }

    private HttpClientFactory setupHttpClientFactory(InputStream responseStream, URI expectedURI) throws IOException {
        Assert.assertNotNull("null response stream!", responseStream);
        HttpEntity httpEntity = mock(HttpEntity.class);
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(responseStream);
        when(httpClient.execute(Matchers.argThat(new ArgumentMatcher<HttpUriRequest>() {

            @Override
            public boolean matches(Object o) {
                Assert.assertThat(o, instanceOf(HttpGet.class));
                HttpGet httpGet = (HttpGet)o;
                Assert.assertEquals(expectedURI, httpGet.getURI());
                return true;
            }
        }))).thenReturn(httpResponse);
        HttpClientFactory httpClientFactory = () -> httpClient;
        return httpClientFactory;
    }
}
