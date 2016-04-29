package net.ngeor.bprr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PullRequestResponseBuilderTest {
    @Test
    public void shouldSetId() {
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withId(123).build();
        assertEquals(123, response.getId());
    }
}
