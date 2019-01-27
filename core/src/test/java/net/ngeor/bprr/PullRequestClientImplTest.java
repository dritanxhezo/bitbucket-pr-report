package net.ngeor.bprr;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.ngeor.bitbucket.Link;
import net.ngeor.bitbucket.Links;
import net.ngeor.bitbucket.PullRequest;
import net.ngeor.bitbucket.PullRequests;
import net.ngeor.http.JsonHttpClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PullRequestClientImpl}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class PullRequestClientImplTest {
    @Mock
    private JsonHttpClient bitbucketClient;

    @InjectMocks
    private PullRequestClientImpl pullRequestClient;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFetchSinglePage() throws IOException {
        PullRequestsRequest request   = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequests expectedResponse = mock(PullRequests.class);
        when(bitbucketClient.read("https://api.bitbucket.org/2.0/" + request, PullRequests.class))
            .thenReturn(expectedResponse);

        // act
        PullRequests actualResponse = pullRequestClient.load(request);

        // assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldFetchAllPages_singlePage() throws IOException {
        PullRequestsRequest request        = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequest justOne                = new PullRequest();
        PullRequests expectedFirstResponse = new PullRequests(justOne);

        when(bitbucketClient.read("https://api.bitbucket.org/2.0/" + request, PullRequests.class))
            .thenReturn(expectedFirstResponse);

        // act
        List<PullRequest> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertThat(actualResponse).containsExactly(justOne);
    }

    @Test
    void shouldFetchAllPages_twoPages() throws IOException {
        PullRequestsRequest request        = new PullRequestsRequest(new RepositoryDescriptor("user", "repo"));
        PullRequest a                      = new PullRequest();
        PullRequest b                      = new PullRequest();
        PullRequests expectedFirstResponse = new PullRequests(a);
        expectedFirstResponse.setNext("link to second request");

        PullRequests expectedSecondResponse = new PullRequests(b);
        when(bitbucketClient.read("https://api.bitbucket.org/2.0/" + request, PullRequests.class))
            .thenReturn(expectedFirstResponse);
        when(bitbucketClient.read("link to second request", PullRequests.class)).thenReturn(expectedSecondResponse);

        // act
        List<PullRequest> actualResponse = pullRequestClient.loadAllPages(request);

        // assert
        assertThat(actualResponse).containsExactly(a, b);
    }

    @Test
    void shouldFetchDetailsOfPullRequests() throws IOException {
        PullRequest firstPullRequest = mock(PullRequest.class);
        PullRequest partialResponse  = createPartialPullRequest("http://first-pr");

        when(bitbucketClient.read("http://first-pr", PullRequest.class)).thenReturn(firstPullRequest);

        // act
        PullRequest actual = pullRequestClient.loadDetails(partialResponse);

        // assert
        assertThat(actual).isEqualTo(firstPullRequest);
    }

    @Test
    void shouldFetchDetailsOfAllPullRequests() throws IOException {
        // the initial request for the pull requests
        PullRequestsRequest pullRequestsRequest =
            new PullRequestsRequest(new RepositoryDescriptor("acme", "project"), PullRequestsRequest.State.Declined);

        PullRequest partial1 = createPartialPullRequest("http://full1");
        PullRequest full1    = createPartialPullRequest("");
        PullRequests page1   = new PullRequests(partial1);
        page1.setNext("http://page2");

        PullRequest partial2 = createPartialPullRequest("http://full2");
        PullRequest full2    = createPartialPullRequest("");
        PullRequests page2   = new PullRequests(partial2);

        doReturn(page1)
            .when(bitbucketClient)
            .read("https://api.bitbucket.org/2.0/repositories/acme/project/pullrequests?q=state+%3D+%22DECLINED%22",
                  PullRequests.class);
        doReturn(page2).when(bitbucketClient).read("http://page2", PullRequests.class);
        doReturn(full1).when(bitbucketClient).read("http://full1", PullRequest.class);
        doReturn(full2).when(bitbucketClient).read("http://full2", PullRequest.class);

        // act
        List<PullRequest> actualPullRequests = pullRequestClient.loadAllDetails(pullRequestsRequest);

        // assert
        assertThat(actualPullRequests).containsExactly(full1, full2);
    }

    private static PullRequest createPartialPullRequest(String href) {
        Link selfLink = new Link(href);

        Links links = new Links().self(selfLink);

        return new PullRequest().links(links);
    }
}
