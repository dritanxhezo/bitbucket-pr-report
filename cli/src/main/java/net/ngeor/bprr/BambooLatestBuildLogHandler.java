package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import net.ngeor.bamboo.BuildResult;
import net.ngeor.bamboo.PlanResults;
import net.ngeor.http.HttpClient;
import net.ngeor.http.JsonHttpClient;

/**
 * Handles the log command.
 */
public class BambooLatestBuildLogHandler {
    /**
     * Handles the command.
     * @param jsonHttpClient
     * @param simpleHttpClient
     * @param programOptions
     * @param out
     * @throws IOException
     */
    public void handle(JsonHttpClient jsonHttpClient,
                       HttpClient simpleHttpClient,
                       ProgramOptions programOptions,
                       final PrintStream out) throws IOException {
        String company = programOptions.getOwner();
        String planKey = programOptions.getRepository();
        String url     = String.format(
            "https://%s.jira.com/builds/rest/api/latest/result/%s.json?os_authType=basic", company, planKey);
        PlanResults bambooPlanResuls              = jsonHttpClient.read(url, PlanResults.class);
        PlanResults.ResultsWrapper resultsWrapper = bambooPlanResuls.getResults();
        BuildResult[] buildResults                = resultsWrapper.getResult();
        BuildResult buildResultSummary            = buildResults[0];
        String logLink                            = buildResultSummary.getLogFileUrl(programOptions.getJobName());
        try (InputStream inputStream = simpleHttpClient.read(logLink)) {
            inputStream.transferTo(out);
        }
    }
}
