package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.Link;
import net.ngeor.bprr.serialization.PullRequestResponse;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
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

    @Test
    public void shouldFetchDetailsOfPullRequests() throws IOException {
        BitbucketClient bitbucketClient = mock(BitbucketClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsResponse pullRequestsResponse = mock(PullRequestsResponse.class);
        PullRequestResponse firstPullRequest = mock(PullRequestResponse.class);
        PullRequestResponse secondPullRequest = mock(PullRequestResponse.class);
        PullRequestResponse[] partialResponses = new PullRequestResponse[] {
                mockResponse("http://first-pr"),
                mockResponse("http://second-pr")
        };
        when(pullRequestsResponse.getValues()).thenReturn(partialResponses);

        when(bitbucketClient.execute("http://first-pr", PullRequestResponse.class)).thenReturn(firstPullRequest);
        when(bitbucketClient.execute("http://second-pr", PullRequestResponse.class)).thenReturn(secondPullRequest);

        // act
        List<PullRequestResponse> pullRequests = pullRequestClient.loadDetails(pullRequestsResponse);

        // assert
        assertEquals(2, pullRequests.size());
        assertEquals(firstPullRequest, pullRequests.get(0));
        assertEquals(secondPullRequest, pullRequests.get(1));
    }

    @Test
    public void shouldFetchDetailsOfAllPullRequests() throws IOException {
        // the initial request for the pull requests
        PullRequestsRequest pullRequestsRequest = mock(PullRequestsRequest.class);

        // first page of partial results
        PullRequestsResponse firstPage = mock(PullRequestsResponse.class);

        // second page of partial results
        PullRequestsResponse secondPage = mock(PullRequestsResponse.class);

        // partial results
        List<PullRequestsResponse> partialPullRequests = Arrays.asList(firstPage, secondPage);

        // detailed pull requests
        PullRequestResponse[] detailedPullRequests = new PullRequestResponse[] {
                mockResponse("pr1"),
                mockResponse("pr2"),
                mockResponse("pr3"),
                mockResponse("pr4")
        };


        PullRequestClient pullRequestClient = mock(PullRequestClientImpl.class);
        when(pullRequestClient.loadAllPages(pullRequestsRequest)).thenReturn(partialPullRequests);
        when(pullRequestClient.loadDetails(firstPage))
                .thenReturn(Arrays.asList(detailedPullRequests[0], detailedPullRequests[1]));
        when(pullRequestClient.loadDetails(secondPage))
                .thenReturn(Arrays.asList(detailedPullRequests[2], detailedPullRequests[3]));

        when(pullRequestClient.loadAllDetails(pullRequestsRequest)).thenCallRealMethod();

        // act
        List<PullRequestResponse> actualPullRequests = pullRequestClient.loadAllDetails(pullRequestsRequest);

        // assert
        assertArrayEquals(detailedPullRequests, actualPullRequests.toArray());
    }

    private static PullRequestResponse mockResponse(String href) {
        PullRequestResponse response = mock(PullRequestResponse.class);
        PullRequestResponse.Links links = mock(PullRequestResponse.Links.class);
        Link selfLink = mock(Link.class);
        when(response.getLinks()).thenReturn(links);
        when(links.getSelf()).thenReturn(selfLink);
        when(selfLink.getHref()).thenReturn(href);
        return response;
    }
}
