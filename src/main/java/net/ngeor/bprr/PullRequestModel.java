package net.ngeor.bprr;

import java.util.Arrays;
import java.util.Date;

public class PullRequestModel {
    private final int id;
    private final String description;
    private final String state;
    private final String author;
    private final String[] reviewers;
    private final Date createdOn;
    private final Date updatedOn;

    // TODO add closed_by

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

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getReviewers() {
        return reviewers;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestModel that = (PullRequestModel) o;

        if (id != that.id) return false;
        if (!description.equals(that.description)) {
            return false;
        }
        if (!state.equals(that.state)) {
            return false;
        }
        if (!createdOn.equals(that.createdOn)) {
            return false;
        }
        if (!updatedOn.equals(that.updatedOn)) {
            return false;
        }
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(reviewers, that.reviewers);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 11 * result + description.hashCode();
        result = 13 * result + state.hashCode();
        result = 17 * result + createdOn.hashCode();
        result = 17 * result + updatedOn.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(reviewers);
        return result;
    }

    @Override
    public String toString() {
        return "PullRequestModel{" +
                "id=" + id +
                ", description=" + description +
                ", state=" + state +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", author='" + author + '\'' +
                ", reviewers=" + Arrays.toString(reviewers) +
                '}';
    }
}
