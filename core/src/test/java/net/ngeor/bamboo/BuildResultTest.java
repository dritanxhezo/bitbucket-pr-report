package net.ngeor.bamboo;

import org.junit.jupiter.api.Test;

import net.ngeor.testutil.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link BuildResult}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class BuildResultTest {
    @Test
    public void shouldLoad() {
        BuildResult buildResult = TestData.load(BuildResult.class, "Simple");
        assertNotNull(buildResult);

        assertEquals("Finished", buildResult.getLifeCycleState());
        assertEquals("Successful", buildResult.getState());
        assertEquals("https://company.jira.com/builds/rest/api/latest/result/PRJ-PLN-1031",
                     buildResult.getLink().getHref());
        assertEquals(798, buildResult.getBuildDurationInSeconds());
        assertEquals(2965, buildResult.getSuccessfulTestCount());
        assertEquals(51, buildResult.getSkippedTestCount());
    }

    @Test
    public void shouldProvideLinkToLog() {
        BuildResult buildResult = TestData.load(BuildResult.class, "Simple");
        assertEquals("https://company.jira.com/builds/download/PRJ-PLN-JOB1/build_logs/PRJ-PLN-JOB1-1031.log",
                     buildResult.getLogFileUrl("JOB1"));
    }
}
