package net.ngeor.bprr;

import java.util.Arrays;

public class PullRequestModel {
    private final int id;
    private final String author;
    private final String[] reviewers;

    public PullRequestModel(int id, String author, String... reviewers) {
        this.id = id;
        this.author = author;
        this.reviewers = reviewers;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getReviewers() {
        return reviewers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestModel that = (PullRequestModel) o;

        if (id != that.id) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(reviewers, that.reviewers);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(reviewers);
        return result;
    }
}
