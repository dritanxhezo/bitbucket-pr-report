package net.ngeor.bprr;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Pull request model.
 */
public class PullRequestModel {
    private int id;
    private String description;
    private String state;
    private String author;
    private String authorTeam;
    private String[] reviewers;
    private String[] reviewerTeams;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public PullRequestModel() {
    }

    public PullRequestModel(int id) {
        this.id = id;
    }

    /**
     * Creates an instance of this class.
     *
     * @param id
     * @param description
     * @param state
     * @param createdOn
     * @param updatedOn
     * @param author
     * @param reviewers
     */
    public PullRequestModel(int id,
                            String description,
                            String state,
                            LocalDateTime createdOn,
                            LocalDateTime updatedOn,
                            String author,
                            String... reviewers) {
        this.id          = id;
        this.description = description;
        this.state       = state;
        this.createdOn   = createdOn;
        this.updatedOn   = updatedOn;
        this.author      = author;
        this.reviewers   = reviewers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorTeam() {
        return authorTeam;
    }

    public void setAuthorTeam(String authorTeam) {
        this.authorTeam = authorTeam;
    }

    public String[] getReviewers() {
        return reviewers;
    }

    public void setReviewers(String[] reviewers) {
        this.reviewers = reviewers;
    }

    public String[] getReviewerTeams() {
        return reviewerTeams;
    }

    public void setReviewerTeams(String[] reviewerTeams) {
        this.reviewerTeams = reviewerTeams;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "PullRequestModel{"
            + "id=" + id + ", description='" + description + '\'' + ", state='" + state + '\'' + ", author='" + author
            + '\'' + ", authorTeam='" + authorTeam + '\'' + ", reviewers=" + Arrays.toString(reviewers)
            + ", reviewerTeams=" + Arrays.toString(reviewerTeams) + ", createdOn=" + createdOn
            + ", updatedOn=" + updatedOn + '}';
    }

    /**
     * With reviewers.
     * @param reviewers
     * @return
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public PullRequestModel reviewers(String... reviewers) {
        this.reviewers = reviewers;
        return this;
    }
}
