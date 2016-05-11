package net.ngeor.bprr;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

public class PullRequestModel {
    private int id;
    private String description;
    private String state;
    private String author;
    private String authorTeam;
    private String[] reviewers;
    private String[] reviewerTeams;
    private Date createdOn;
    private Date updatedOn;

    // TODO add closed_by

    public PullRequestModel() {

    }

    public PullRequestModel(int id) {
        this.id = id;
    }

    public PullRequestModel(int id, String description, String state, Date createdOn, Date updatedOn, String author, String... reviewers) {
        this.id = id;
        this.description = description;
        this.state = state;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.author = author;
        this.reviewers = reviewers;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestModel that = (PullRequestModel) o;

        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (authorTeam != null ? !authorTeam.equals(that.authorTeam) : that.authorTeam != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(reviewers, that.reviewers)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(reviewerTeams, that.reviewerTeams)) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        return updatedOn != null ? updatedOn.equals(that.updatedOn) : that.updatedOn == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (authorTeam != null ? authorTeam.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(reviewers);
        result = 31 * result + Arrays.hashCode(reviewerTeams);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PullRequestModel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", author='" + author + '\'' +
                ", authorTeam='" + authorTeam + '\'' +
                ", reviewers=" + Arrays.toString(reviewers) +
                ", reviewerTeams=" + Arrays.toString(reviewerTeams) +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
