package net.ngeor.bprr;

import net.ngeor.bprr.serialization.BambooBuildResult;
import net.ngeor.bprr.serialization.BambooPlanResults;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

public class BambooLatestBuildHandler {
    public void handle(RestClient restClient, ProgramOptions programOptions, PrintStream out) throws IOException {
        String company = programOptions.getUser();
        String planKey = programOptions.getRepository();
        String url = String.format("https://%s.jira.com/builds/rest/api/latest/result/%s.json?os_authType=basic", company, planKey);
        BambooPlanResults bambooPlanResuls = restClient.execute(url, BambooPlanResults.class);
        BambooPlanResults.ResultsWrapper resultsWrapper = bambooPlanResuls.getResults();
        BambooBuildResult[] buildResults = resultsWrapper.getResult();
        BambooBuildResult buildResultSummary = buildResults[0];
        String buildResultDetailsUrl = buildResultSummary.getLink().getHref() + ".json?os_authType=basic";
        BambooBuildResult buildResultDetails = restClient.execute(buildResultDetailsUrl, BambooBuildResult.class);

        out.println(String.format(Locale.ROOT, "successfulTestCount %d", buildResultDetails.getSuccessfulTestCount()));
        out.println(String.format(Locale.ROOT, "skippedTestCount %d", buildResultDetails.getSkippedTestCount()));
    }
}
