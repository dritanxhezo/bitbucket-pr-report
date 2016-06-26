package net.ngeor.bprr;

import net.ngeor.bitbucket.Link;
import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;
import net.ngeor.bitbucket.PullRequestsRequest;
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
        RestClient bitbucketClient = mock(RestClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequests expectedResponse = mock(PullRequests.class);
        when(bitbucketClient.execute(request, PullRequests.class)).thenReturn(expectedResponse);

        // act
        PullRequests actualResponse = pullRequestClient.load(request);

        // assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldFetchAllPages_singlePage() throws IOException {
        RestClient bitbucketClient = mock(RestClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequests expectedFirstResponse = mock(PullRequests.class);
        when(bitbucketClient.execute(request, PullRequests.class)).thenReturn(expectedFirstResponse);

        // act
        List<PullRequests> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertEquals(1, actualResponse.size());
        assertEquals(expectedFirstResponse, actualResponse.get(0));
    }

    @Test
    public void shouldFetchAllPages_twoPages() throws IOException {
        RestClient bitbucketClient = mock(RestClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequests expectedFirstResponse = mock(PullRequests.class);
        when(expectedFirstResponse.getNext()).thenReturn("link to second request");
        PullRequests expectedSecondResponse = mock(PullRequests.class);
        when(bitbucketClient.execute(request, PullRequests.class)).thenReturn(expectedFirstResponse);
        when(bitbucketClient.execute("link to second request", PullRequests.class)).thenReturn(expectedSecondResponse);

        // act
        List<PullRequests> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertEquals(2, actualResponse.size());
        assertEquals(expectedFirstResponse, actualResponse.get(0));
        assertEquals(expectedSecondResponse, actualResponse.get(1));
    }


    @Test
    public void shouldFetchAllPages_threePages() throws IOException {
        RestClient bitbucketClient = mock(RestClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequests expectedFirstResponse = mock(PullRequests.class);
        when(expectedFirstResponse.getNext()).thenReturn("link to second request");
        PullRequests expectedSecondResponse = mock(PullRequests.class);
        when(expectedSecondResponse.getNext()).thenReturn("link to third request");
        PullRequests expectedThirdResponse = mock(PullRequests.class);
        when(bitbucketClient.execute(request, PullRequests.class)).thenReturn(expectedFirstResponse);
        when(bitbucketClient.execute("link to second request", PullRequests.class)).thenReturn(expectedSecondResponse);
        when(bitbucketClient.execute("link to third request", PullRequests.class)).thenReturn(expectedThirdResponse);

        // act
        List<PullRequests> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertEquals(3, actualResponse.size());
        assertEquals(expectedFirstResponse, actualResponse.get(0));
        assertEquals(expectedSecondResponse, actualResponse.get(1));
        assertEquals(expectedThirdResponse, actualResponse.get(2));
    }

    @Test
    public void shouldFetchDetailsOfPullRequests() throws IOException {
        RestClient bitbucketClient = mock(RestClient.class);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        PullRequests pullRequestsResponse = mock(PullRequests.class);
        PullRequest firstPullRequest = mock(PullRequest.class);
        PullRequest secondPullRequest = mock(PullRequest.class);
        PullRequest[] partialResponses = new PullRequest[] {
                mockResponse("http://first-pr"),
                mockResponse("http://second-pr")
        };
        when(pullRequestsResponse.getValues()).thenReturn(partialResponses);

        when(bitbucketClient.execute("http://first-pr", PullRequest.class)).thenReturn(firstPullRequest);
        when(bitbucketClient.execute("http://second-pr", PullRequest.class)).thenReturn(secondPullRequest);

        // act
        List<PullRequest> pullRequests = pullRequestClient.loadDetails(pullRequestsResponse);

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
        PullRequests firstPage = mock(PullRequests.class);

        // second page of partial results
        PullRequests secondPage = mock(PullRequests.class);

        // partial results
        List<PullRequests> partialPullRequests = Arrays.asList(firstPage, secondPage);

        // detailed pull requests
        PullRequest[] detailedPullRequests = new PullRequest[] {
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
        List<PullRequest> actualPullRequests = pullRequestClient.loadAllDetails(pullRequestsRequest);

        // assert
        assertArrayEquals(detailedPullRequests, actualPullRequests.toArray());
    }

    private static PullRequest mockResponse(String href) {
        PullRequest response = mock(PullRequest.class);
        PullRequest.Links links = mock(PullRequest.Links.class);
        Link selfLink = mock(Link.class);
        when(response.getLinks()).thenReturn(links);
        when(links.getSelf()).thenReturn(selfLink);
        when(selfLink.getHref()).thenReturn(href);
        return response;
    }
}
