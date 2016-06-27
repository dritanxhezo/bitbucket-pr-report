package net.ngeor.bprr;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ProgramOptions {
    private final Options options;
    private CommandLine commandLine;

    public int getStartDaysDiff() {
        if (!commandLine.hasOption("start-days-diff")) {
            return 0;
        }

        return Integer.parseInt(commandLine.getOptionValue("start-days-diff"));
    }

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
        options.addOption(new EnumOption<>("c", "command", "the command to run", Command.class));
        options.addOption("u", "user", true, "the user name that owns the repositories");
        options.addOption("s", "secret", true, "base64 encoded authentication token");
        options.addOption("r", "repository", true, "the repository slug");
        options.addOption("t", "team", false, "group pull requests by team");
        options.addOption("j", "job", true, "the job name of a certain bamboo build");

        options.addOption(Option.builder()
            .longOpt("start-days-diff")
            .hasArg()
            .desc("Number of days to subtract from current date")
            .build());
    }

    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("bprr", options);
    }

    public void parse(String... args) throws ParseException {
        // create the parser
        CommandLineParser commandLineParser = new DefaultParser();
        commandLine = commandLineParser.parse(options, args);
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

class EnumOption<E extends Enum> extends Option {

    public EnumOption(String opt, String longOpt, String description, Class<E> enumClass) throws IllegalArgumentException {
        super(opt, longOpt, true /* hasArg */, description + " " + ArrayUtils.toString(enumClass.getEnumConstants()));
        setRequired(true);
    }
}
