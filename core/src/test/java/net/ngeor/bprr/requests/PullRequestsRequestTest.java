package net.ngeor.bprr.requests;

import net.ngeor.bprr.RepositoryDescriptor;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;
import org.joda.time.LocalDate;
import org.junit.Test;

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
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.utcDate(2016, 5, 4).toLocalDate(), null);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-04", url);
    }

    @Test
    public void shouldFormatUpdatedOnWithOnlyUntil() {
        LocalDateInterval updatedOn = new LocalDateInterval(null, DateHelper.utcDate(2016, 5, 4).toLocalDate());
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+2016-05-04", url);
    }

    @Test
    public void shouldSetUpdatedOnUntilWhenDateIsTomorrow() {
        LocalDate tomorrow = new LocalDate().plusDays(1);
        LocalDateInterval updatedOn = new LocalDateInterval(null, tomorrow);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+" + tomorrow.toString(), url);
    }

    @Test
    public void shouldSetUpdatedOnUntilWhenDateIsToday() {
        LocalDate today = DateHelper.utcToday().toLocalDate();
        LocalDateInterval updatedOn = new LocalDateInterval(null, today);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+" + today.toString(), url);
    }

    @Test
    public void shouldFormatUpdatedOnWithBothFromAndUntil() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.utcDate(2016, 5, 1).toLocalDate(), DateHelper.utcDate(2016, 5, 4).toLocalDate());
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-01+AND+updated_on+%3C+2016-05-04", url);
    }

    @Test
    public void shouldFormatStateAndUpdatedOn() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.utcDate(2016, 5, 3).toLocalDate(), null);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), PullRequestsRequest.State.Merged, updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22+AND+updated_on+%3E%3D+2016-05-03", url);
    }
}
