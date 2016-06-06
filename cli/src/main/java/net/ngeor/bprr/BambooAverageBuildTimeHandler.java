package net.ngeor.bprr;

import net.ngeor.bprr.serialization.BambooPlan;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

public class BambooAverageBuildTimeHandler {
    public void handle(RestClient restClient, ProgramOptions programOptions, PrintStream out) throws IOException {
        String company = programOptions.getUser();
        String planKey = programOptions.getRepository();
        String zabbixHost = programOptions.getZabbixHost();
        String zabbixKey = programOptions.getZabbixKey();
        String url = String.format("https://%s.jira.com/builds/rest/api/latest/plan/%s.json?os_authType=basic", company, planKey);
        BambooPlan bambooPlan = restClient.execute(url, BambooPlan.class);
        double timeInMinutes = bambooPlan.getAverageBuildTimeInSeconds() / 60.0;
        out.println(String.format(Locale.ROOT, "%s %s %.2f", zabbixHost, zabbixKey, timeInMinutes));
    }
}
