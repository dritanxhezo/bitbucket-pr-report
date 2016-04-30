package net.ngeor.bprr;

import net.ngeor.dates.DateHelper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class PullRequestResponseBuilderTest {
    @Test
    public void shouldSetId() {
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withId(123).build();
        assertEquals(123, response.getId());
    }
    
    @Test
    public void shouldSetDescription() {
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withDescription("hello").build();
        assertEquals("hello", response.getDescription());
    }

    @Test
    public void shouldSetState() {
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withState("hello").build();
        assertEquals("hello", response.getState());
    }

    @Test
    public void shouldSetCreatedOn() {
        Date dt = DateHelper.date(2016, 4, 30);
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withCreatedOn(dt).build();
        assertEquals(dt, response.getCreatedOn());
    }

    @Test
    public void shouldSetUpdatedOn() {
        Date dt = DateHelper.date(2016, 4, 30);
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withUpdatedOn(dt).build();
        assertEquals(dt, response.getUpdatedOn());
    }

    @Test
    public void shouldSetAuthor() {
        Author author = new Author("ngeor", "Nikolaos");
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withAuthor(author).build();
        assertEquals(author, response.getAuthor());
    }

    @Test
    public void shouldSetParticipants() {
        Participant[] participants = new Participant[] {
            new Participant(true, "reviewer", new Author("ngeor", "Nikolaos"))
        };
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withParticipants(participants).build();
        assertArrayEquals(participants, response.getParticipants());
    }

    @Test
    public void shouldRememberId() {
        // arrange
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withId(123).build();

        // act
        PullRequestResponse secondResponse = builder.build();

        // assert
        assertNotSame(response, secondResponse);
        assertEquals(123, secondResponse.getId());
    }

    @Test
    public void shouldClearId() {
        // arrange
        PullRequestResponseBuilder builder = new PullRequestResponseBuilder();
        PullRequestResponse response = builder.withId(123).build();
        builder.reset();

        // act
        PullRequestResponse secondResponse = builder.build();

        // assert
        assertEquals(0, secondResponse.getId());
    }
}
