package net.ngeor.bprr;

import net.ngeor.bprr.serialization.BambooPlan;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BambooAverageBuildTimeHandlerTest {
    @Test
    public void shouldReportTimeInMinutes() throws IOException {
        // arrange
        RestClient restClient = mock(RestClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out = mock(PrintStream.class);
        BambooPlan bambooPlan = mock(BambooPlan.class);

        when(programOptions.getUser()).thenReturn("company");
        when(programOptions.getRepository()).thenReturn("planKey");
        when(restClient.execute("https://company.jira.com/builds/rest/api/latest/plan/planKey.json?os_authType=basic", BambooPlan.class))
                .thenReturn(bambooPlan);
        when(bambooPlan.getAverageBuildTimeInSeconds()).thenReturn(120.0);

        // act
        BambooAverageBuildTimeHandler handler = new BambooAverageBuildTimeHandler();
        handler.handle(restClient, programOptions, out);

        // assert
        verify(out).println("2.00");
    }
}
