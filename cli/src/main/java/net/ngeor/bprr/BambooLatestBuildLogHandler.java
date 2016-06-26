package net.ngeor.bprr;

import net.ngeor.bamboo.BuildResult;
import net.ngeor.bamboo.PlanResults;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class BambooLatestBuildLogHandler {
    public void handle(RestClient restClient, SimpleHttpClient simpleHttpClient, ProgramOptions programOptions, final PrintStream out) throws IOException {
        String company = programOptions.getUser();
        String planKey = programOptions.getRepository();
        String url = String.format("https://%s.jira.com/builds/rest/api/latest/result/%s.json?os_authType=basic", company, planKey);
        PlanResults bambooPlanResuls = restClient.execute(url, PlanResults.class);
        PlanResults.ResultsWrapper resultsWrapper = bambooPlanResuls.getResults();
        BuildResult[] buildResults = resultsWrapper.getResult();
        BuildResult buildResultSummary = buildResults[0];
        String logLink = buildResultSummary.getLogFileUrl(programOptions.getJobName());
        simpleHttpClient.load(logLink, programOptions.getSecret(), new InputStreamClient<Object>() {
            @Override
            public Object consume(InputStream inputStream) throws IOException {
                IOUtils.copy(inputStream, out);
                return null;
            }
        });
    }
}
