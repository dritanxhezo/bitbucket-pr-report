package net.ngeor.bprr.requests;

import net.ngeor.util.DateHelper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PullRequestsRequestTest {
    @Test
    public void shouldFormatOwnerAndRepository() {
        PullRequestsRequest request = new PullRequestsRequest("ngeor", "bprr");
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests", url);
    }

    @Test
    public void shouldFormatState() {
        PullRequestsRequest request = new PullRequestsRequest("ngeor", "bprr", PullRequestsRequest.State.Merged);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22", url);
    }

    @Test
    public void shouldFormatUpdatedOn() {
        Date updatedOn = DateHelper.utcDate(2016, 5, 4);
        PullRequestsRequest request = new PullRequestsRequest("ngeor", "bprr", updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-04", url);
    }

    @Test
    public void shouldFormatStateAndUpdatedOn() {
        Date updatedOn = DateHelper.utcDate(2016, 5, 3);
        PullRequestsRequest request = new PullRequestsRequest("ngeor", "bprr", PullRequestsRequest.State.Merged, updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22+AND+updated_on+%3E%3D+2016-05-03", url);
    }
}
