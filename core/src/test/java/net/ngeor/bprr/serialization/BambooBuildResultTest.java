package net.ngeor.bprr.serialization;

import net.ngeor.testutil.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BambooBuildResultTest {
    @Test
    public void shouldLoad() {
        BambooBuildResult bambooBuildResult = TestData.load(BambooBuildResult.class, "Simple");
        assertNotNull(bambooBuildResult);

        assertEquals("Finished", bambooBuildResult.getLifeCycleState());
        assertEquals("Successful", bambooBuildResult.getState());
        assertEquals("https://company.jira.com/builds/rest/api/latest/result/PRJ-PLN-1031", bambooBuildResult.getLink().getHref());
        assertEquals(798, bambooBuildResult.getBuildDurationInSeconds());
        assertEquals(2965, bambooBuildResult.getSuccessfulTestCount());
        assertEquals(51, bambooBuildResult.getSkippedTestCount());
    }
}
