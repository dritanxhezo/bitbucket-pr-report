package net.ngeor.bprr;

import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import net.ngeor.bamboo.Plan;
import net.ngeor.http.JsonHttpClient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link BambooAverageBuildTimeHandler}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class BambooAverageBuildTimeHandlerTest {
    @Test
    void shouldReportTimeInMinutes() throws IOException {
        // arrange
        JsonHttpClient jsonHttpClient = mock(JsonHttpClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out               = mock(PrintStream.class);
        Plan plan                     = mock(Plan.class);

        when(programOptions.getOwner()).thenReturn("company");
        when(programOptions.getRepository()).thenReturn("planKey");
        when(jsonHttpClient.read("https://company.jira.com/builds/rest/api/latest/plan/planKey.json?os_authType=basic",
                                 Plan.class))
            .thenReturn(plan);
        when(plan.getAverageBuildTimeInSeconds()).thenReturn(120.0);

        // act
        BambooAverageBuildTimeHandler handler = new BambooAverageBuildTimeHandler();
        handler.handle(jsonHttpClient, programOptions, out);

        // assert
        verify(out).println("2.00");
    }
}
