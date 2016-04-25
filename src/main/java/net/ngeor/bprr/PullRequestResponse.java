package net.ngeor.bprr;

public class PullRequestResponse {
    private String description;
    private Author author;
    private int id;
    private Participant[] participants;

    public PullRequestResponse() {

    }

    public PullRequestResponse(int id, String description, Author author, Participant... participants) {
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

    public Author getAuthor() {
        return author;
    }

    public Participant[] getParticipants() {
        return participants;
    }

}
