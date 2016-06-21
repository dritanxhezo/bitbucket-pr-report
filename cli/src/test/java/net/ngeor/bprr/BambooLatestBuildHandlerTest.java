package net.ngeor.bprr;

import net.ngeor.bprr.serialization.BambooBuildResult;
import net.ngeor.bprr.serialization.BambooPlanResults;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BambooLatestBuildHandlerTest {
    @Test
    public void shouldReportTestCount() throws IOException {
        // arrange
        RestClient restClient = mock(RestClient.class);
        ProgramOptions programOptions = mock(ProgramOptions.class);
        PrintStream out = mock(PrintStream.class);
        BambooPlanResults bambooPlanResults = mock(BambooPlanResults.class);
        BambooPlanResults.ResultsWrapper resultsWrapper = mock(BambooPlanResults.ResultsWrapper.class);
        BambooBuildResult successfulBuild = mock(BambooBuildResult.class);

        BambooBuildResult result[] = new BambooBuildResult[] {
            successfulBuild
        };

        when(programOptions.getUser()).thenReturn("company");
        when(programOptions.getRepository()).thenReturn("planKey");
        when(restClient.execute("https://company.jira.com/builds/rest/api/latest/result/planKey.json?os_authType=basic", BambooPlanResults.class))
                .thenReturn(bambooPlanResults);
        when(bambooPlanResults.getResults()).thenReturn(resultsWrapper);
        when(resultsWrapper.getResult()).thenReturn(result);
        when(successfulBuild.getSuccessfulTestCount()).thenReturn(37);
        when(successfulBuild.getSkippedTestCount()).thenReturn(17);

        // act
        BambooLatestBuildHandler handler = new BambooLatestBuildHandler();
        handler.handle(restClient, programOptions, out);

        // assert
        verify(out).println("successfulTestCount 37");
        verify(out).println("skippedTestCount 17");
    }
}
