package net.ngeor.bprr;

import org.apache.commons.lang3.StringUtils;

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

        HttpClientFactory httpClientFactory = new HttpClientFactoryImpl();
        RestClient bitbucketClient = new BitbucketClientImpl(httpClientFactory, secret);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bitbucketClient);

        ProgramOptions.Command command = programOptions.getCommand();
        switch (command) {
            case OpenPullRequests:
                handleOpenPullRequests(pullRequestClient, programOptions);
                break;
            case MergedPullRequests:
                handleMergedPullRequests(pullRequestClient, programOptions);
                break;
            case BambooAverageBuildTime:
                handleBambooAverageBuildTime(httpClientFactory, programOptions);
                break;
            default:
                System.err.println("No command speciried");
                break;
        }
    }

    private static void handleOpenPullRequests(PullRequestClient pullRequestClient, ProgramOptions programOptions) throws IOException {
        OpenPullRequestsHandler handler = new OpenPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, System.out);
    }

    private static void handleMergedPullRequests(PullRequestClient pullRequestClient, ProgramOptions programOptions) throws IOException {
        MergedPullRequestsHandler handler = new MergedPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, System.out);
    }

    private static void handleBambooAverageBuildTime(HttpClientFactory httpClientFactory, ProgramOptions programOptions) throws IOException {
        BambooAverageBuildTimeHandler handler = new BambooAverageBuildTimeHandler();
        RestClient restClient = new RestClientImpl(httpClientFactory, programOptions.getSecret());
        handler.handle(restClient, programOptions, System.out);
    }
}
