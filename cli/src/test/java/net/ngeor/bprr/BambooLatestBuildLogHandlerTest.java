package net.ngeor.bprr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import net.ngeor.bamboo.BuildResult;
import net.ngeor.bamboo.PlanResults;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link BambooLatestBuildHandler}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class BambooLatestBuildLogHandlerTest {
    @Test
    public void shouldOutputLog() throws IOException {
        // arrange
        RestClient restClient                     = mock(RestClient.class);
        ProgramOptions programOptions             = mock(ProgramOptions.class);
        ByteArrayOutputStream out                 = new ByteArrayOutputStream();
        PlanResults planResults                   = mock(PlanResults.class);
        PlanResults.ResultsWrapper resultsWrapper = mock(PlanResults.ResultsWrapper.class);
        BuildResult successfulBuildSummary        = mock(BuildResult.class);
        String logFileContents                    = "This is the log file of the build";
        InputStream logFileAsInputStream          = IOUtils.toInputStream(logFileContents, "UTF-8");
        SimpleHttpClient simpleHttpClient         = setupHttpClientFactory(
            logFileAsInputStream,
            "https://company.jira.com/builds/download/PRJ-PLN-JOB1/build_logs/PRJ-PLN-JOB1-1090.log");

        var result = new BuildResult[] {successfulBuildSummary};

        when(programOptions.getUser()).thenReturn("company");
        when(programOptions.getRepository()).thenReturn("planKey");
        when(programOptions.getSecret()).thenReturn("some secret");
        when(programOptions.getJobName()).thenReturn("JOB1");
        when(restClient.execute("https://company.jira.com/builds/rest/api/latest/result/planKey.json?os_authType=basic",
                                PlanResults.class))
            .thenReturn(planResults);
        when(planResults.getResults()).thenReturn(resultsWrapper);
        when(resultsWrapper.getResult()).thenReturn(result);
        when(successfulBuildSummary.getLogFileUrl("JOB1"))
            .thenReturn("https://company.jira.com/builds/download/PRJ-PLN-JOB1/build_logs/PRJ-PLN-JOB1-1090.log");

        // act
        BambooLatestBuildLogHandler handler = new BambooLatestBuildLogHandler();
        handler.handle(restClient, simpleHttpClient, programOptions, new PrintStream(out));

        // assert
        String output = out.toString("UTF8");
        assertEquals(logFileContents, output);
    }

    private static SimpleHttpClient setupHttpClientFactory(final InputStream responseStream, final String expectedURI) {
        assertThat(responseStream).as("response stream!").isNotNull();
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient() {
            @Override
            public <E> E load(String url, String basicAuthenticationHeader, InputStreamClient<E> inputStreamClient)
                throws IOException {
                assertEquals(expectedURI, url);
                assertEquals(basicAuthenticationHeader, "some secret");
                return inputStreamClient.consume(responseStream);
            }
        };
        return simpleHttpClient;
    }
}
