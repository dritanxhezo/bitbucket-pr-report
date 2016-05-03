package net.ngeor.bprr;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PullRequestsRequest {
    private final String owner;
    private final String repositorySlug;
    private final State state;

    public PullRequestsRequest(@NotNull String owner, @NotNull String repositorySlug, @NotNull State state) {
        this.owner = owner;
        this.repositorySlug = repositorySlug;
        this.state = state;
    }

    @Override
    public String toString() {
        // TODO: configure date
        String q = "state = \"" + state.toString().toUpperCase() + "\" AND updated_on >= 2016-04-25";
        try {
            q = URLEncoder.encode(q, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "repositories/" + owner + "/" + repositorySlug
                + "/pullrequests?"
                + "q=" + q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestsRequest that = (PullRequestsRequest) o;

        if (!owner.equals(that.owner)) return false;
        if (!state.equals(that.state)) return false;
        return repositorySlug.equals(that.repositorySlug);

    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 11 * result + state.hashCode();
        result = 31 * result + repositorySlug.hashCode();
        return result;
    }

    public enum State {
        Open,
        Merged,
        Declined
    }
}
