package net.ngeor.bprr.serialization;

import net.ngeor.testutil.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BambooPlanResultsTest {
    @Test
    public void shouldRead() {
        BambooPlanResults bambooPlanResults = TestData.load(BambooPlanResults.class, "Simple");
        assertNotNull(bambooPlanResults);

        BambooPlanResults.ResultsWrapper resultsWrapper = bambooPlanResults.getResults();
        assertNotNull(resultsWrapper);

        BambooPlanResults.Result[] buildResults = resultsWrapper.getResult();
        assertNotNull(buildResults);

        BambooPlanResults.Result firstBuildResult = buildResults[0];
        assertNotNull(firstBuildResult);

        assertEquals("PRJ-PLN-1031", firstBuildResult.getKey());
        assertEquals("Finished", firstBuildResult.getLifeCycleState());
        assertEquals("Successful", firstBuildResult.getState());
    }
}
