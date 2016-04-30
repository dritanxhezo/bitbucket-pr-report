package net.ngeor.bprr;

import java.util.Date;

public class PullRequestResponse {
    private int id;
    private String description;
    private String state;
    private Date created_on;
    private Date updated_on;
    private Author author;
    private Participant[] participants;


    public PullRequestResponse() {

    }

    PullRequestResponse(PullRequestResponseBuilder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.state = builder.state;
        this.created_on = builder.createdOn;
        this.updated_on = builder.updatedOn;
        this.author = builder.author;
        this.participants = builder.participants;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public Author getAuthor() {
        return author;
    }

    public Participant[] getParticipants() {
        return participants;
    }

    public Date getCreatedOn() {
        return created_on;
    }

    public Date getUpdatedOn() { return updated_on; }
}
