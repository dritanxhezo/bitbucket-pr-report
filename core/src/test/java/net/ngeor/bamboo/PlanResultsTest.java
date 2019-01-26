package net.ngeor.bamboo;

import org.junit.jupiter.api.Test;

import net.ngeor.testutil.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link PlanResults}.
 */
public class PlanResultsTest {
    @Test
    public void shouldRead() {
        PlanResults planResults = TestData.load(PlanResults.class, "Simple");
        assertNotNull(planResults);

        PlanResults.ResultsWrapper resultsWrapper = planResults.getResults();
        assertNotNull(resultsWrapper);

        BuildResult[] buildResults = resultsWrapper.getResult();
        assertNotNull(buildResults);

        BuildResult firstBuildResult = buildResults[0];
        assertNotNull(firstBuildResult);

        assertEquals("PRJ-PLN-1031", firstBuildResult.getKey());
        assertEquals("Finished", firstBuildResult.getLifeCycleState());
        assertEquals("Successful", firstBuildResult.getState());
        assertEquals("https://company.jira.com/builds/rest/api/latest/result/PRJ-PLN-1031",
                     firstBuildResult.getLink().getHref());
    }
}
