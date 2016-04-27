package net.ngeor.bprr;

public class PullRequestResponse {
    private int id;
    private String description;
    private String state;
    private Author author;
    private Participant[] participants;

    public PullRequestResponse() {

    }

    public PullRequestResponse(int id, String description, String state, Author author, Participant... participants) {
        this.id = id;
        this.description = description;
        this.author = author;
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

}
