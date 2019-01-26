package net.ngeor.bprr;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Program options.
 */
public class ProgramOptions {
    private final Options options;
    private CommandLine commandLine;

    /**
     * Creates an instance of this class.
     */
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

    /**
     * Parses the start days diff option.
     * @return
     */
    public int getStartDaysDiff() {
        if (!commandLine.hasOption("start-days-diff")) {
            return 0;
        }

        return Integer.parseInt(commandLine.getOptionValue("start-days-diff"));
    }

    /**
     * The available commands.
     */
    public enum Command {
        /**
         * Open pull requests.
         */
        OpenPullRequests,

        /**
         * Merged pull requests.
         */
        MergedPullRequests,

        /**
         * Bamboo average build time.
         */
        BambooAverageBuildTime,

        /**
         * Bamboo latest build.
         */
        BambooLatestBuild,

        /**
         * Log of bamboo latest build.
         */
        BambooLatestBuildLog
    }

    public void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("bprr", options);
    }

    public void parse(String... args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        commandLine                         = commandLineParser.parse(options, args);
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

    /**
     * Gets the command.
     * @return
     */
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

/**
 * An enum option.
 * @param <E>
 */
class EnumOption<E extends Enum> extends Option {

    /**
     * Creates an instance of this class.
     * @param opt
     * @param longOpt
     * @param description
     * @param enumClass
     * @throws IllegalArgumentException
     */
    EnumOption(String opt, String longOpt, String description, Class<E> enumClass) throws IllegalArgumentException {
        super(opt, longOpt, true /* hasArg */, description + " " + ArrayUtils.toString(enumClass.getEnumConstants()));
        setRequired(true);
    }
}
