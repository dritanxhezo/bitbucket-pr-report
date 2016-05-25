package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PullRequestClientImplTest {
    @Test
    public void shouldFetchSinglePage() throws IOException {
        BitbucketClient bitbucketClient = mock(BitbucketClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest("user", "repo");
        PullRequestsResponse expectedResponse = mock(PullRequestsResponse.class);
        when(bitbucketClient.execute(request, PullRequestsResponse.class)).thenReturn(expectedResponse);

        // act
        PullRequestsResponse actualResponse = pullRequestClient.load(request);

        // assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldFetchAllPages_singlePage() throws IOException {
        BitbucketClient bitbucketClient = mock(BitbucketClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest("user", "repo");
        PullRequestsResponse expectedFirstResponse = mock(PullRequestsResponse.class);
        when(bitbucketClient.execute(request, PullRequestsResponse.class)).thenReturn(expectedFirstResponse);

        // act
        List<PullRequestsResponse> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertEquals(1, actualResponse.size());
        assertEquals(expectedFirstResponse, actualResponse.get(0));
    }

    @Test
    public void shouldFetchAllPages_twoPages() throws IOException {
        BitbucketClient bitbucketClient = mock(BitbucketClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest("user", "repo");
        PullRequestsResponse expectedFirstResponse = mock(PullRequestsResponse.class);
        when(expectedFirstResponse.getNext()).thenReturn("link to second request");
        PullRequestsResponse expectedSecondResponse = mock(PullRequestsResponse.class);
        when(bitbucketClient.execute(request, PullRequestsResponse.class)).thenReturn(expectedFirstResponse);
        when(bitbucketClient.execute("link to second request", PullRequestsResponse.class)).thenReturn(expectedSecondResponse);

        // act
        List<PullRequestsResponse> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertEquals(2, actualResponse.size());
        assertEquals(expectedFirstResponse, actualResponse.get(0));
        assertEquals(expectedSecondResponse, actualResponse.get(1));
    }


    @Test
    public void shouldFetchAllPages_threePages() throws IOException {
        BitbucketClient bitbucketClient = mock(BitbucketClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest("user", "repo");
        PullRequestsResponse expectedFirstResponse = mock(PullRequestsResponse.class);
        when(expectedFirstResponse.getNext()).thenReturn("link to second request");
        PullRequestsResponse expectedSecondResponse = mock(PullRequestsResponse.class);
        when(expectedSecondResponse.getNext()).thenReturn("link to third request");
        PullRequestsResponse expectedThirdResponse = mock(PullRequestsResponse.class);
        when(bitbucketClient.execute(request, PullRequestsResponse.class)).thenReturn(expectedFirstResponse);
        when(bitbucketClient.execute("link to second request", PullRequestsResponse.class)).thenReturn(expectedSecondResponse);
        when(bitbucketClient.execute("link to third request", PullRequestsResponse.class)).thenReturn(expectedThirdResponse);

        // act
        List<PullRequestsResponse> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertEquals(3, actualResponse.size());
        assertEquals(expectedFirstResponse, actualResponse.get(0));
        assertEquals(expectedSecondResponse, actualResponse.get(1));
        assertEquals(expectedThirdResponse, actualResponse.get(2));
    }
}
