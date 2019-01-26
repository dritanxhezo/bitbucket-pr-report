package net.ngeor.bprr;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link ProgramOptions}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class ProgramOptionsTest {
    @Test
    public void shouldParseUserWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-u", "ngeor");
        assertEquals("ngeor", programOptions.getUser());
    }

    @Test
    public void shouldParseUserWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--user", "ngeor");
        assertEquals("ngeor", programOptions.getUser());
    }

    @Test
    public void shouldParseSecretWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-s", "confidential");
        assertEquals("confidential", programOptions.getSecret());
    }

    @Test
    public void shouldParseSecretWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--secret", "confidential");
        assertEquals("confidential", programOptions.getSecret());
    }

    @Test
    public void shouldParseRepositoryWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-r", "my repository");
        assertEquals("my repository", programOptions.getRepository());
    }

    @Test
    public void shouldParseRepositoryWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--repository", "my repository");
        assertEquals("my repository", programOptions.getRepository());
    }

    @Test
    public void shouldParseOpenPullRequestsCommandWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertEquals(ProgramOptions.Command.OpenPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldParseOpenPullRequestsCommandWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--command", "OpenPullRequests");
        assertEquals(ProgramOptions.Command.OpenPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldParseMergedPullRequestsCommandWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "MergedPullRequests");
        assertEquals(ProgramOptions.Command.MergedPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldParseMergedPullRequestsCommandWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--command", "MergedPullRequests");
        assertEquals(ProgramOptions.Command.MergedPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldReturnNullCommandWhenOptionIsMissing() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        assertThatThrownBy(programOptions::parse).isInstanceOf(ParseException.class);
    }

    @Test
    public void shouldParseTeamWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-t");
        assertTrue(programOptions.isGroupByTeam());
    }

    @Test
    public void shouldParseTeamWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--team");
        assertTrue(programOptions.isGroupByTeam());
    }

    @Test
    public void shouldNotRequireTeamOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertFalse(programOptions.isGroupByTeam());
    }

    @Test
    public void shouldParseJobNameWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-j", "JOB12");
        assertEquals("JOB12", programOptions.getJobName());
    }

    @Test
    public void shouldParseJobNameWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--job", "JOB4");
        assertEquals("JOB4", programOptions.getJobName());
    }

    @Test
    public void shouldParseStartDaysDiffWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--start-days-diff", "4");
        assertEquals(4, programOptions.getStartDaysDiff());
    }

    @Test
    public void shouldReturnZeroWhenStartDaysDiffIsMissing() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertEquals(0, programOptions.getStartDaysDiff());
    }
}
