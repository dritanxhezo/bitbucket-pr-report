package net.ngeor.bprr;

import net.ngeor.bprr.requests.PullRequestsRequest;
import net.ngeor.bprr.serialization.PullRequestsResponse;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar:*" net.ngeor.bprr.Program -u user -s secret -r repositorySlug

        // create the options
        Options options = new Options();
        options.addOption("u", "user", true, "the user name that owns the repositories");
        options.addOption("s", "secret", true, "base64 encoded authentication token");
        options.addOption("r", "repository", true, "the repository slug");

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
        PullRequestsRequest request = new PullRequestsRequest(user, repositorySlug, PullRequestsRequest.State.Open);
        PullRequestsResponse pullRequestsResponse = bitbucketClient.execute(request, PullRequestsResponse.class);

        // generate output that can be used with zabbix_sender
        System.out.println("mini.local bitbucket.open.pull.requests " + pullRequestsResponse.getSize());
    }
}
