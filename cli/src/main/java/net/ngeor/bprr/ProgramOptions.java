package net.ngeor.bprr;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

public class ProgramOptions {
    private final Options options;
    private CommandLine commandLine;

    public enum Command {
        OpenPullRequests,
        MergedPullRequests,
        BambooAverageBuildTime,
        BambooLatestBuild,
        BambooLatestBuildLog
    }

    public ProgramOptions() {
        // create the options
        options = new Options();
        options.addOption("u", "user", true, "the user name that owns the repositories");
        options.addOption("s", "secret", true, "base64 encoded authentication token");
        options.addOption("r", "repository", true, "the repository slug");
        options.addOption("c", "command", true, "the command to run [ OpenPullRequests, MergedPullRequests, BambooAverageBuildTime ]");
        options.addOption("t", "team", false, "group pull requests by team");
        options.addOption("j", "job", true, "the job name of a certain bamboo build");
    }

    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("bprr", options);
    }

    public boolean parse(String... args) {
        // create the parser
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException ex) {
            System.err.println("Parsing failed. Reason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    public String getUser() {
        return commandLine.getOptionValue("user");
    }

    public String getSecret() {
        return commandLine.getOptionValue("secret");
    }

    public String getRepository() {
        return commandLine.getOptionValue("repository");
    }

    public Command getCommand() {
        String option = commandLine.getOptionValue("command");
        if (StringUtils.isBlank(option)) {
            return null;
        }

        return Command.valueOf(commandLine.getOptionValue("command"));
    }

    public String getJobName() {
        return commandLine.getOptionValue("job");
    }

    public boolean isGroupByTeam() {
        return commandLine.hasOption('t');
    }
}
