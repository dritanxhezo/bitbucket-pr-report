package net.ngeor.bprr;

import java.io.IOException;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import net.ngeor.http.HttpClient;
import net.ngeor.http.HttpClientImpl;
import net.ngeor.http.JsonHttpClient;
import net.ngeor.http.JsonHttpClientImpl;
import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;

/**
 * Main program.
 */
public final class Program {
    private Program() {
    }

    /**
     * Main entrypoint.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // To run on *nix:
        // java -cp "bprr-cli-1.0-SNAPSHOT.jar:*" net.ngeor.bprr.Program -u user -s
        // secret -r repositorySlug To run on Windows: java -cp
        // "bprr-cli-1.0-SNAPSHOT.jar;*" net.ngeor.bprr.Program

        ProgramOptions programOptions = new ProgramOptions();
        try {
            programOptions.parse(args);
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            programOptions.printHelp();
            return;
        }

        final String owner          = programOptions.getOwner();
        final String username       = programOptions.getUsername();
        final String password       = programOptions.getPassword();
        final String repositorySlug = programOptions.getRepository();
        if (StringUtils.isBlank(owner) || StringUtils.isBlank(username) || StringUtils.isBlank(password)
            || StringUtils.isBlank(repositorySlug)) {
            programOptions.printHelp();
            return;
        }

        HttpClient simpleHttpClient         = new HttpClientImpl(username, password);
        JsonHttpClient bambooClient         = new JsonHttpClientImpl(simpleHttpClient);
        PullRequestClient pullRequestClient = new PullRequestClientImpl(bambooClient);
        ResourceLoader resourceLoader       = new ResourceLoaderImpl();
        TeamMapperImpl teamMapper           = new TeamMapperImpl(resourceLoader);
        teamMapper.loadFromProperties();

        ProgramOptions.Command command = programOptions.getCommand();
        switch (command) {
            case OpenPullRequests:
                handleOpenPullRequests(pullRequestClient, programOptions, teamMapper);
                break;
            case MergedPullRequests:
                handleMergedPullRequests(pullRequestClient, programOptions);
                break;
            case BambooAverageBuildTime:
                handleBambooAverageBuildTime(bambooClient, programOptions);
                break;
            case BambooLatestBuild:
                handleBambooLatestBuild(bambooClient, programOptions);
                break;
            case BambooLatestBuildLog:
                handleBambooLatestBuildLog(bambooClient, simpleHttpClient, programOptions);
                break;
            default:
                System.err.println("No command specified");
                break;
        }
    }

    private static void handleOpenPullRequests(PullRequestClient pullRequestClient,
                                               ProgramOptions programOptions,
                                               TeamMapper teamMapper) throws IOException {
        OpenPullRequestsHandler handler = new OpenPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, System.out, teamMapper);
    }

    private static void handleMergedPullRequests(PullRequestClient pullRequestClient, ProgramOptions programOptions)
        throws IOException {
        MergedPullRequestsHandler handler = new MergedPullRequestsHandler();
        handler.handle(pullRequestClient, programOptions, System.out);
    }

    private static void handleBambooAverageBuildTime(JsonHttpClient jsonHttpClient, ProgramOptions programOptions)
        throws IOException {
        BambooAverageBuildTimeHandler handler = new BambooAverageBuildTimeHandler();
        handler.handle(jsonHttpClient, programOptions, System.out);
    }

    private static void handleBambooLatestBuild(JsonHttpClient jsonHttpClient, ProgramOptions programOptions)
        throws IOException {
        BambooLatestBuildHandler handler = new BambooLatestBuildHandler();
        handler.handle(jsonHttpClient, programOptions, System.out);
    }

    private static void handleBambooLatestBuildLog(JsonHttpClient jsonHttpClient,
                                                   HttpClient simpleHttpClient,
                                                   ProgramOptions programOptions) throws IOException {
        BambooLatestBuildLogHandler handler = new BambooLatestBuildLogHandler();
        handler.handle(jsonHttpClient, simpleHttpClient, programOptions, System.out);
    }
}
