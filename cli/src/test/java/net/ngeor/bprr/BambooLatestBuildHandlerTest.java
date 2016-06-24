package net.ngeor.bprr;

import net.ngeor.bprr.serialization.BambooBuildResult;
import net.ngeor.bprr.serialization.BambooPlanResults;
import net.ngeor.bprr.serialization.Link;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class BambooLatestBuildHandlerTest {
    @Test
    public void shouldReportTestCount() throws IOException {
        // arrange
        RestClient restClient = mock(RestClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out = mock(PrintStream.class);
        BambooPlanResults bambooPlanResults = mock(BambooPlanResults.class);
        BambooPlanResults.ResultsWrapper resultsWrapper = mock(BambooPlanResults.ResultsWrapper.class);
        BambooBuildResult successfulBuildSummary = mock(BambooBuildResult.class);
        BambooBuildResult successfulBuildDetails = mock(BambooBuildResult.class);

        BambooBuildResult result[] = new BambooBuildResult[] {
            successfulBuildSummary
        };

        when(programOptions.getUser()).thenReturn("company");
        when(programOptions.getRepository()).thenReturn("planKey");
        when(restClient.execute("https://company.jira.com/builds/rest/api/latest/result/planKey.json?os_authType=basic", BambooPlanResults.class))
                .thenReturn(bambooPlanResults);
        when(restClient.execute("https://whatever.json?os_authType=basic", BambooBuildResult.class))
                .thenReturn(successfulBuildDetails);
        when(bambooPlanResults.getResults()).thenReturn(resultsWrapper);
        when(resultsWrapper.getResult()).thenReturn(result);
        when(successfulBuildSummary.getLink()).thenReturn(new Link("https://whatever"));
        when(successfulBuildDetails.getSuccessfulTestCount()).thenReturn(37);
        when(successfulBuildDetails.getSkippedTestCount()).thenReturn(17);

        // act
        BambooLatestBuildHandler handler = new BambooLatestBuildHandler();
        handler.handle(restClient, programOptions, out);

        // assert
        verify(out).println("successfulTestCount 37");
        verify(out).println("skippedTestCount 17");
    }
}
