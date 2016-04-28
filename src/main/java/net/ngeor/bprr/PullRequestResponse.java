package net.ngeor.bprr;

import java.util.Date;

public class PullRequestResponse {
    private int id;
    private String description;
    private String state;
    private Author author;
    private Participant[] participants;
    private Date created_on;

    public PullRequestResponse() {

    }

    public PullRequestResponse(int id, String description, String state, Date createdOn, Author author, Participant... participants) {
        this.id = id;
        this.description = description;
        this.author = author;
        this.state = state;
        this.created_on = createdOn;
        this.participants = participants;
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
}
