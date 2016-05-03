package net.ngeor.bprr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PullRequestsRequestTest {
    @Test
    public void shouldReturnTheUrlInToString() {
        PullRequestsRequest request = new PullRequestsRequest("ngeor", "bprr", PullRequestsRequest.State.Merged);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22+AND+updated_on+%3E%3D+2016-04-25", url);
    }
}
