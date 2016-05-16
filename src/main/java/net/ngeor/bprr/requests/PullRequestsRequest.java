package net.ngeor.bprr.requests;

import net.ngeor.util.DateRange;
import net.ngeor.util.URLQueryWriter;
import net.ngeor.util.URLStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PullRequestsRequest {
    private final String owner;
    private final String repositorySlug;
    private final State state;
    private final DateRange updatedOn;

    public PullRequestsRequest(@NotNull String owner, @NotNull String repositorySlug, State state, DateRange updatedOn) {
        if (owner == null || owner.isEmpty()) {
            throw new IllegalArgumentException("owner cannot be null");
        }

        if (repositorySlug == null || repositorySlug.isEmpty()) {
            throw new IllegalArgumentException("repositorySlug cannot be null");
        }

        this.owner = owner;
        this.repositorySlug = repositorySlug;
        this.state = state;
        this.updatedOn = updatedOn;
    }

    public PullRequestsRequest(@NotNull String owner, @NotNull String repositorySlug) {
        this(owner, repositorySlug, null, null);
    }

    public PullRequestsRequest(@NotNull String owner, @NotNull String repositorySlug, State state) {
        this(owner, repositorySlug, state, null);
    }

    public PullRequestsRequest(@NotNull String owner, @NotNull String repositorySlug, DateRange updatedOn) {
        this(owner, repositorySlug, null, updatedOn);
    }

    private static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    @Override
    public String toString() {
        URLStringBuilder result = new URLStringBuilder();
        URLQueryWriter urlQueryWriter = new URLQueryWriter(result);

        result.append("repositories/").append(owner).append("/").append(repositorySlug).append("/pullrequests");
        if (state != null || updatedOn != null) {
            result.append("?q=");

            if (state != null) {
                urlQueryWriter.write("state", "=", "\"" + state.toString().toUpperCase() + "\"");
            }

            if (updatedOn != null) {
                if (updatedOn.getFrom() != null) {
                    urlQueryWriter.write("updated_on", ">=", formatDate(updatedOn.getFrom()));
                }

                if (updatedOn.getUntil() != null) {
                    urlQueryWriter.write("updated_on", "<", formatDate(updatedOn.getUntil()));
                }
            }
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestsRequest that = (PullRequestsRequest) o;

        if (!owner.equals(that.owner)) return false;
        if (!repositorySlug.equals(that.repositorySlug)) return false;
        if (state != that.state) return false;
        return updatedOn != null ? updatedOn.equals(that.updatedOn) : that.updatedOn == null;

    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + repositorySlug.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

    public enum State {
        Open,
        Merged,
        Declined
    }
}
