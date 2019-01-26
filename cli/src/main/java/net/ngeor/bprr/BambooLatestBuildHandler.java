package net.ngeor.bprr;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import net.ngeor.bamboo.BuildResult;
import net.ngeor.bamboo.PlanResults;

/**
 * Handles the latest build command.
 */
public class BambooLatestBuildHandler {
    /**
     * Handles the command.
     * @param restClient
     * @param programOptions
     * @param out
     * @throws IOException
     */
    public void handle(RestClient restClient, ProgramOptions programOptions, PrintStream out) throws IOException {
        String company = programOptions.getUser();
        String planKey = programOptions.getRepository();
        String url     = String.format(
            "https://%s.jira.com/builds/rest/api/latest/result/%s.json?os_authType=basic", company, planKey);
        PlanResults bambooPlanResuls              = restClient.execute(url, PlanResults.class);
        PlanResults.ResultsWrapper resultsWrapper = bambooPlanResuls.getResults();
        BuildResult[] buildResults                = resultsWrapper.getResult();
        BuildResult buildResultSummary            = buildResults[0];
        String buildResultDetailsUrl              = buildResultSummary.getLink().getHref() + ".json?os_authType=basic";
        BuildResult buildResultDetails            = restClient.execute(buildResultDetailsUrl, BuildResult.class);

        out.println(String.format(Locale.ROOT, "successfulTestCount %d", buildResultDetails.getSuccessfulTestCount()));
        out.println(String.format(Locale.ROOT, "skippedTestCount %d", buildResultDetails.getSkippedTestCount()));
    }
}
