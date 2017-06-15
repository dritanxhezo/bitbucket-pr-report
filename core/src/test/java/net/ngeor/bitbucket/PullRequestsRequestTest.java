package net.ngeor.bitbucket;

import net.ngeor.bprr.RepositoryDescriptor;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PullRequestsRequestTest {
    @Test
    public void shouldFormatOwnerAndRepository() {
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"));
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests", url);
    }

    @Test
    public void shouldFormatState() {
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), PullRequestsRequest.State.Merged);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22", url);
    }

    @Test
    public void shouldFormatUpdatedOn() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.localDate(2016, 5, 4), null);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-04", url);
    }

    @Test
    public void shouldFormatUpdatedOnWithOnlyUntil() {
        LocalDateInterval updatedOn = new LocalDateInterval(null, DateHelper.localDate(2016, 5, 4));
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+2016-05-04", url);
    }

    @Test
    public void shouldSetUpdatedOnUntilWhenDateIsTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateInterval updatedOn = new LocalDateInterval(null, tomorrow);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+" + tomorrow.toString(), url);
    }

    @Test
    public void shouldSetUpdatedOnUntilWhenDateIsToday() {
        LocalDate today = DateHelper.utcToday();
        LocalDateInterval updatedOn = new LocalDateInterval(null, today);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+" + today.toString(), url);
    }

    @Test
    public void shouldFormatUpdatedOnWithBothFromAndUntil() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.localDate(2016, 5, 1), DateHelper.localDate(2016, 5, 4));
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-01+AND+updated_on+%3C+2016-05-04", url);
    }

    @Test
    public void shouldFormatStateAndUpdatedOn() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.localDate(2016, 5, 3), null);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), PullRequestsRequest.State.Merged, updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22+AND+updated_on+%3E%3D+2016-05-03", url);
    }
}
