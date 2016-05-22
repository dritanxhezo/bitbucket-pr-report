package net.ngeor.bprr.serialization;

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

    public Date getUpdatedOn() {
        return updated_on;
    }
}
