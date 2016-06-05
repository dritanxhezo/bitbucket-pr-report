package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.BambooPlan;
import net.ngeor.bprr.serialization.PullRequests;
import net.ngeor.util.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Interval;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        // To run on *nix:
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar:*" net.ngeor.bprr.Program -u user -s secret -r repositorySlug
        // To run on Windows:
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar;*" net.ngeor.bprr.Program

        ProgramOptions programOptions = new ProgramOptions();
        if (!programOptions.parse(args)){
            return;
        }

        final String user = programOptions.getUser();
        final String secret = programOptions.getSecret();
        final String repositorySlug = programOptions.getRepository();
        if (StringUtils.isBlank(user) || StringUtils.isBlank(secret) || StringUtils.isBlank(repositorySlug)) {
            programOptions.printHelp();
            return;
        }

        String zabbixHost = programOptions.getZabbixHost();
        String zabbixKey = programOptions.getZabbixKey();

        // echo mini.local bitbucket.open.pull.requests 0 | zabbix_sender -z localhost -vv -i -
        HttpClientFactory httpClientFactory = new HttpClientFactoryImpl();
        Settings settings = new SettingsImpl(user, secret);
        RestClient bitbucketClient = new BitbucketClientImpl(httpClientFactory, settings);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor(user, repositorySlug);

        ProgramOptions.Command command = programOptions.getCommand();
        switch (command) {
            case OpenPullRequests:
                handleOpenPullRequests(repositoryDescriptor, pullRequestClient, zabbixHost, zabbixKey);
                break;
            case MergedPullRequests:
                handleMergedPullRequests(repositoryDescriptor, pullRequestClient, zabbixHost, zabbixKey);
                break;
            case BambooAverageBuildTime:
                handleBambooAverageBuildTime(httpClientFactory, settings, programOptions);
                break;
            default:
                System.err.println("No command speciried");
        }
    }

    private static void handleMergedPullRequests(RepositoryDescriptor repositoryDescriptor, PullRequestClient pullRequestClient, String zabbixHost, String zabbixKey) throws IOException {
        PullRequestsRequest request = new PullRequestsRequest(
                repositoryDescriptor,
                PullRequestsRequest.State.Merged,
                new Interval(DateHelper.utcToday().minusDays(1), DateHelper.utcToday()));
        PullRequests pullRequests = pullRequestClient.load(request);

        // generate output that can be used with zabbix_sender
        System.out.println(zabbixHost + " " + zabbixKey + " " + pullRequests.getSize());
    }

    private static void handleOpenPullRequests(RepositoryDescriptor repositoryDescriptor, PullRequestClient pullRequestClient, String zabbixHost, String zabbixKey) throws IOException {
        // TODO create class OpenPullRequestsHandler
        PullRequestsRequest request = new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Open);
        PullRequests pullRequests = pullRequestClient.load(request);

        // generate output that can be used with zabbix_sender
        System.out.println(zabbixHost + " " + zabbixKey + " " + pullRequests.getSize());
    }

    private static void handleBambooAverageBuildTime(HttpClientFactory httpClientFactory, Settings settings, ProgramOptions programOptions) throws IOException {
        RestClient restClient = new RestClientImpl(httpClientFactory, settings);
        String company = programOptions.getUser();
        String planKey = programOptions.getRepository();
        String zabbixHost = programOptions.getZabbixHost();
        String zabbixKey = programOptions.getZabbixKey();
        String url = String.format("https://%s.jira.com/builds/rest/api/latest/plan/%s.json?os_authType=basic", company, planKey);
        BambooPlan bambooPlan = restClient.execute(url, BambooPlan.class);
        double timeInMinutes = bambooPlan.getAverageBuildTimeInSeconds() / 60.0;
        System.out.println(String.format("%s %s %f", zabbixHost, zabbixKey, timeInMinutes));
    }
}
