package net.ngeor.bprr.requests;

import net.ngeor.bprr.RepositoryDescriptor;
import net.ngeor.util.LocalDateInterval;
import net.ngeor.util.URLQueryWriter;
import net.ngeor.util.URLStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

public class PullRequestsRequest {
    private final RepositoryDescriptor repositoryDescriptor;
    private final State state;
    private final LocalDateInterval updatedOn;

    public PullRequestsRequest(@NotNull RepositoryDescriptor repositoryDescriptor, State state, LocalDateInterval updatedOn) {
        if (repositoryDescriptor == null) {
            throw new IllegalArgumentException("repositoryDescriptor cannot be null");
        }

        this.repositoryDescriptor = repositoryDescriptor;
        this.state = state;
        this.updatedOn = updatedOn;
    }

    public PullRequestsRequest(@NotNull RepositoryDescriptor repositoryDescriptor) {
        this(repositoryDescriptor, null, null);
    }

    public PullRequestsRequest(@NotNull RepositoryDescriptor repositoryDescriptor, State state) {
        this(repositoryDescriptor, state, null);
    }

    public PullRequestsRequest(@NotNull RepositoryDescriptor repositoryDescriptor, LocalDateInterval updatedOn) {
        this(repositoryDescriptor, null, updatedOn);
    }

    private static String formatDate(LocalDate date) {
        return date.toString();
    }

    @Override
    public String toString() {
        URLStringBuilder result = new URLStringBuilder();
        URLQueryWriter urlQueryWriter = new URLQueryWriter(result);

        result.append("repositories/").append(repositoryDescriptor.toString()).append("/pullrequests");
        if (state != null || updatedOn != null) {
            result.append("?q=");

            if (state != null) {
                urlQueryWriter.write("state", "=", "\"" + state.toString().toUpperCase() + "\"");
            }

            if (updatedOn != null) {
                if (updatedOn.getStart() != null) {
                    urlQueryWriter.write("updated_on", ">=", formatDate(updatedOn.getStart()));
                }

                if (updatedOn.getEnd() != null) {
                    urlQueryWriter.write("updated_on", "<", formatDate(updatedOn.getEnd()));
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

        if (!repositoryDescriptor.equals(that.repositoryDescriptor)) return false;
        if (state != that.state) return false;
        return updatedOn != null ? updatedOn.equals(that.updatedOn) : that.updatedOn == null;

    }

    @Override
    public int hashCode() {
        int result = repositoryDescriptor.hashCode();
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
