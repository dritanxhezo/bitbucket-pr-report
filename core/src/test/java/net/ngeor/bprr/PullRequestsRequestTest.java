package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ngeor.bitbucket.Repositories;
import net.ngeor.http.HttpClient;
import net.ngeor.http.HttpClientImpl;
import net.ngeor.json.ObjectMapperFactory;
import net.ngeor.util.DateHelper;
import net.ngeor.util.LocalDateInterval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link PullRequestsRequest}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class PullRequestsRequestTest {
    @Test
    void shouldFormatOwnerAndRepository() {
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"));
        String url                  = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests", url);
    }

    @Test
    void shouldFormatState() {
        PullRequestsRequest request =
            new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), PullRequestsRequest.State.Merged);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22", url);
    }

    @Test
    void shouldFormatUpdatedOn() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.localDate(2016, 5, 4), null);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url                  = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-04", url);
    }

    @Test
    void shouldFormatUpdatedOnWithOnlyUntil() {
        LocalDateInterval updatedOn = new LocalDateInterval(null, DateHelper.localDate(2016, 5, 4));
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url                  = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+2016-05-04", url);
    }

    @Test
    void shouldSetUpdatedOnUntilWhenDateIsTomorrow() {
        LocalDate tomorrow          = LocalDate.now().plusDays(1);
        LocalDateInterval updatedOn = new LocalDateInterval(null, tomorrow);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url                  = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+" + tomorrow.toString(), url);
    }

    @Test
    void shouldSetUpdatedOnUntilWhenDateIsToday() {
        LocalDate today             = DateHelper.utcToday();
        LocalDateInterval updatedOn = new LocalDateInterval(null, today);
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url                  = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=updated_on+%3C+" + today.toString(), url);
    }

    @Test
    void shouldFormatUpdatedOnWithBothFromAndUntil() {
        LocalDateInterval updatedOn =
            new LocalDateInterval(DateHelper.localDate(2016, 5, 1), DateHelper.localDate(2016, 5, 4));
        PullRequestsRequest request = new PullRequestsRequest(new RepositoryDescriptor("ngeor", "bprr"), updatedOn);
        String url                  = request.toString();
        assertEquals(
            "repositories/ngeor/bprr/pullrequests?q=updated_on+%3E%3D+2016-05-01+AND+updated_on+%3C+2016-05-04", url);
    }

    @Test
    void shouldFormatStateAndUpdatedOn() {
        LocalDateInterval updatedOn = new LocalDateInterval(DateHelper.localDate(2016, 5, 3), null);
        PullRequestsRequest request = new PullRequestsRequest(
            new RepositoryDescriptor("ngeor", "bprr"), PullRequestsRequest.State.Merged, updatedOn);
        String url = request.toString();
        assertEquals("repositories/ngeor/bprr/pullrequests?q=state+%3D+%22MERGED%22+AND+updated_on+%3E%3D+2016-05-03",
                     url);
    }

    @Test
    @Disabled("only useful for live troubleshooting")
    void getRepositories() throws IOException {
        String url                = "https://api.bitbucket.org/2.0/repositories/acme";
        String username           = "your username";
        String password           = "your password";
        HttpClient httpClient     = new HttpClientImpl(username, password);
        ObjectMapper objectMapper = ObjectMapperFactory.create();

        try (InputStream is = httpClient.read(url)) {
            Repositories repositories = objectMapper.readValue(is, Repositories.class);
            System.out.println(repositories);
        }

        url = url + "/your-project/pullrequests";
        try (InputStream is = httpClient.read(url)) {
            is.transferTo(System.out);
        }
    }

    @Test
    void getRepositoriesMinimal() throws IOException {
        ObjectMapper objectMapper = ObjectMapperFactory.create();
        InputStream is            = getClass().getResourceAsStream("/net/ngeor/bitbucket/repositories-minimal.json");
        assertThat(is).as("input stream").isNotNull();
        Repositories repositories = objectMapper.readValue(is, Repositories.class);
        assertThat(repositories).isNotNull();
    }
}
