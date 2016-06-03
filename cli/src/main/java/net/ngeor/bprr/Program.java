package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequests;
import net.ngeor.util.DateHelper;
import net.ngeor.util.DateRange;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        // To run on *nix:
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar:*" net.ngeor.bprr.Program -u user -s secret -r repositorySlug
        // To run on Windows:
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar;*" net.ngeor.bprr.Program

        // create the options
        Options options = new Options();
        options.addOption("u", "user", true, "the user name that owns the repositories");
        options.addOption("s", "secret", true, "base64 encoded authentication token");
        options.addOption("r", "repository", true, "the repository slug");
        options.addOption("o", "openPullRequests", false, "show open pull requests");
        options.addOption("c", "closedPullRequests", false, "show closed pull requests statistics");

        // create the parser
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException ex) {
            System.err.println("Parsing failed. Reason: " + ex.getMessage());
            return;
        }

        final String user = commandLine.getOptionValue("user");
        final String secret = commandLine.getOptionValue("secret");
        final String repositorySlug = commandLine.getOptionValue("repository");
        if (StringUtils.isBlank(user) || StringUtils.isBlank(secret) || StringUtils.isBlank(repositorySlug)) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("bprr", options);
            return;
        }

        // echo mini.local bitbucket.open.pull.requests 0 | zabbix_sender -z localhost -vv -i -
        HttpClientFactory httpClientFactory = new HttpClientFactoryImpl();
        Settings settings = new SettingsImpl(user, secret);
        BitbucketClient bitbucketClient = new BitbucketClientImpl(httpClientFactory, settings);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);
        RepositoryDescriptor repositoryDescriptor = new RepositoryDescriptor(user, repositorySlug);

        if (commandLine.hasOption('o')) {
            PullRequestsRequest request = new PullRequestsRequest(repositoryDescriptor, PullRequestsRequest.State.Open);
            PullRequests pullRequests = pullRequestClient.load(request);

            // generate output that can be used with zabbix_sender
            System.out.println("mini.local bitbucket.open.pull.requests " + pullRequests.getSize());
        } else if (commandLine.hasOption('c')) {
            PullRequestsRequest request = new PullRequestsRequest(
                    repositoryDescriptor,
                    PullRequestsRequest.State.Merged,
                    new DateRange(new DateTime(DateHelper.utcToday()).minusDays(1).toDate(), DateHelper.utcToday()));
            PullRequests pullRequests = pullRequestClient.load(request);

            // generate output that can be used with zabbix_sender
            System.out.println("mini.local bitbucket.open.pull.requests " + pullRequests.getSize());
        } else {
            System.err.println("No command speciried");
        }
    }
}
