package net.ngeor.bprr;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import net.ngeor.bamboo.Plan;

/**
 * Handles bamboo average build time.
 */
public class BambooAverageBuildTimeHandler {

    private static final double SECONDS_PER_MINUTE = 60.0;

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
            "https://%s.jira.com/builds/rest/api/latest/plan/%s.json?os_authType=basic", company, planKey);
        Plan plan            = restClient.execute(url, Plan.class);
        double timeInMinutes = plan.getAverageBuildTimeInSeconds() / SECONDS_PER_MINUTE;
        out.println(String.format(Locale.ROOT, "%.2f", timeInMinutes));
    }
}
