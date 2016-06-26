package net.ngeor.bprr;

import net.ngeor.bamboo.Plan;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

public class BambooAverageBuildTimeHandler {
    public void handle(RestClient restClient, ProgramOptions programOptions, PrintStream out) throws IOException {
        String company = programOptions.getUser();
        String planKey = programOptions.getRepository();
        String url = String.format("https://%s.jira.com/builds/rest/api/latest/plan/%s.json?os_authType=basic", company, planKey);
        Plan plan = restClient.execute(url, Plan.class);
        double timeInMinutes = plan.getAverageBuildTimeInSeconds() / 60.0;
        out.println(String.format(Locale.ROOT, "%.2f", timeInMinutes));
    }
}
