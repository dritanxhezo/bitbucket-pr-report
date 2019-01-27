package net.ngeor.bitbucket;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A pull request.
 */
public class PullRequest {
    private int id;
    private String description;
    private String state;

    @JsonProperty("created_on")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSz")
    private LocalDateTime createdOn;
    @JsonProperty("updated_on")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSz")
    private LocalDateTime updatedOn;
    private Author author;
    private Participant[] participants;
    private Links links;

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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public PullRequest links(Links links) {
        this.links = links;
        return this;
    }
}
