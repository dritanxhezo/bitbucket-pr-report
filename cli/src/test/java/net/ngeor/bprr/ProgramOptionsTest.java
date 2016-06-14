package net.ngeor.bprr;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProgramOptionsTest {
    @Test
    public void shouldParseUserWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-u", "ngeor");
        assertEquals("ngeor", programOptions.getUser());
    }

    @Test
    public void shouldParseUserWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--user", "ngeor");
        assertEquals("ngeor", programOptions.getUser());
    }

    @Test
    public void shouldParseSecretWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-s", "confidential");
        assertEquals("confidential", programOptions.getSecret());
    }

    @Test
    public void shouldParseSecretWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--secret", "confidential");
        assertEquals("confidential", programOptions.getSecret());
    }

    @Test
    public void shouldParseRepositoryWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-r", "my repository");
        assertEquals("my repository", programOptions.getRepository());
    }

    @Test
    public void shouldParseRepositoryWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--repository", "my repository");
        assertEquals("my repository", programOptions.getRepository());
    }

    @Test
    public void shouldParseOpenPullRequestsCommandWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "OpenPullRequests");
        assertEquals(ProgramOptions.Command.OpenPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldParseOpenPullRequestsCommandWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--command", "OpenPullRequests");
        assertEquals(ProgramOptions.Command.OpenPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldParseMergedPullRequestsCommandWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-c", "MergedPullRequests");
        assertEquals(ProgramOptions.Command.MergedPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldParseMergedPullRequestsCommandWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--command", "MergedPullRequests");
        assertEquals(ProgramOptions.Command.MergedPullRequests, programOptions.getCommand());
    }

    @Test
    public void shouldReturnNullCommandWhenOptionIsMissing() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse();
        assertNull(programOptions.getCommand());
    }

    @Test
    public void shouldParseTeamWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-t");
        assertTrue(programOptions.isGroupByTeam());
    }

    @Test
    public void shouldParseTeamWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--team");
        assertTrue(programOptions.isGroupByTeam());
    }

    @Test
    public void shouldNotRequireTeamOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse();
        assertFalse(programOptions.isGroupByTeam());
    }
}
