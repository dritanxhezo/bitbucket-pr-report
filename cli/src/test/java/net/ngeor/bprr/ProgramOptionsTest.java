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
class ProgramOptionsTest {
    @Test
    void shouldParseOwnerWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-o", "ngeor");
        assertEquals("ngeor", programOptions.getOwner());
    }

    @Test
    void shouldParseOwnerWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--owner", "ngeor");
        assertEquals("ngeor", programOptions.getOwner());
    }

    @Test
    void shouldParseUsernameWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-u", "ngeor");
        assertEquals("ngeor", programOptions.getUsername());
    }

    @Test
    void shouldParseUsernameWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--username", "ngeor");
        assertEquals("ngeor", programOptions.getUsername());
    }

    @Test
    void shouldParsePasswordWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-p", "confidential");
        assertEquals("confidential", programOptions.getPassword());
    }

    @Test
    void shouldParsePasswordWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--password", "confidential");
        assertEquals("confidential", programOptions.getPassword());
    }

    @Test
    void shouldParseRepositoryWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-r", "my repository");
        assertEquals("my repository", programOptions.getRepository());
    }

    @Test
    void shouldParseRepositoryWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--repository", "my repository");
        assertEquals("my repository", programOptions.getRepository());
    }

    @Test
    void shouldParseOpenPullRequestsCommandWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertEquals(ProgramOptions.Command.OpenPullRequests, programOptions.getCommand());
    }

    @Test
    void shouldParseOpenPullRequestsCommandWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--command", "OpenPullRequests");
        assertEquals(ProgramOptions.Command.OpenPullRequests, programOptions.getCommand());
    }

    @Test
    void shouldParseMergedPullRequestsCommandWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "MergedPullRequests");
        assertEquals(ProgramOptions.Command.MergedPullRequests, programOptions.getCommand());
    }

    @Test
    void shouldParseMergedPullRequestsCommandWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--command", "MergedPullRequests");
        assertEquals(ProgramOptions.Command.MergedPullRequests, programOptions.getCommand());
    }

    @Test
    void shouldReturnNullCommandWhenOptionIsMissing() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        assertThatThrownBy(programOptions::parse).isInstanceOf(ParseException.class);
    }

    @Test
    void shouldParseTeamWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-t");
        assertTrue(programOptions.isGroupByTeam());
    }

    @Test
    void shouldParseTeamWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--team");
        assertTrue(programOptions.isGroupByTeam());
    }

    @Test
    void shouldNotRequireTeamOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertFalse(programOptions.isGroupByTeam());
    }

    @Test
    void shouldParseJobNameWithShortOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "-j", "JOB12");
        assertEquals("JOB12", programOptions.getJobName());
    }

    @Test
    void shouldParseJobNameWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--job", "JOB4");
        assertEquals("JOB4", programOptions.getJobName());
    }

    @Test
    void shouldParseStartDaysDiffWithLongOption() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests", "--start-days-diff", "4");
        assertEquals(4, programOptions.getStartDaysDiff());
    }

    @Test
    void shouldReturnZeroWhenStartDaysDiffIsMissing() throws ParseException {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertEquals(0, programOptions.getStartDaysDiff());
    }
}
