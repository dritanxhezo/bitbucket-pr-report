package net.ngeor.bprr;

import net.ngeor.bamboo.BuildResult;
import net.ngeor.bamboo.PlanResults;
import net.ngeor.bitbucket.Link;
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
        PlanResults planResults = mock(PlanResults.class);
        PlanResults.ResultsWrapper resultsWrapper = mock(PlanResults.ResultsWrapper.class);
        BuildResult successfulBuildSummary = mock(BuildResult.class);
        BuildResult successfulBuildDetails = mock(BuildResult.class);

        BuildResult result[] = new BuildResult[] {
            successfulBuildSummary
        };

        when(programOptions.getUser()).thenReturn("company");
        when(programOptions.getRepository()).thenReturn("planKey");
        when(restClient.execute("https://company.jira.com/builds/rest/api/latest/result/planKey.json?os_authType=basic", PlanResults.class))
                .thenReturn(planResults);
        when(restClient.execute("https://whatever.json?os_authType=basic", BuildResult.class))
                .thenReturn(successfulBuildDetails);
        when(planResults.getResults()).thenReturn(resultsWrapper);
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
