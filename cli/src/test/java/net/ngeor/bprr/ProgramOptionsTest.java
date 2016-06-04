package net.ngeor.bprr;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void shouldParseZabbixHostWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-h", "some.host");
        assertEquals("some.host", programOptions.getZabbixHost());
    }

    @Test
    public void shouldParseZabbixHostWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--zabbixHost", "some.host");
        assertEquals("some.host", programOptions.getZabbixHost());
    }

    @Test
    public void shouldParseZabbixKeyWithShortOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("-k", "zabbix.key");
        assertEquals("zabbix.key", programOptions.getZabbixKey());
    }

    @Test
    public void shouldParseZabbixKeyWithLongOption() {
        ProgramOptions programOptions = new ProgramOptions();
        programOptions.parse("--zabbixKey", "zabbix.key");
        assertEquals("zabbix.key", programOptions.getZabbixKey());
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
}
