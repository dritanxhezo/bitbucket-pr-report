package net.ngeor.bprr;

import java.util.Date;

public class PullRequestResponseBuilder {
    int id;
    String description;
    String state;
    Date createdOn;
    Date updatedOn;
    Author author;
    Participant[] participants;

    public PullRequestResponse build() {
        return new PullRequestResponse(this);
    }

    public PullRequestResponseBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public PullRequestResponseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PullRequestResponseBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public PullRequestResponseBuilder withCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public PullRequestResponseBuilder withUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public PullRequestResponseBuilder withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public PullRequestResponseBuilder withParticipants(Participant[] participants) {
        this.participants = participants;
        return this;
    }

    public PullRequestResponseBuilder reset() {
        this.id = 0;
        this.description = null;
        this.state = null;
        this.createdOn = null;
        this.updatedOn = null;
        this.author = null;
        this.participants = null;
        return this;
    }
}
