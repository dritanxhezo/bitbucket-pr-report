package net.ngeor.bitbucket;

import java.time.LocalDate;

import net.ngeor.bprr.RepositoryDescriptor;
import net.ngeor.util.LocalDateInterval;
import net.ngeor.util.URLQueryWriter;
import net.ngeor.util.URLStringBuilder;

/**
 * A request for pull requests.
 */
public class PullRequestsRequest {
    private final RepositoryDescriptor repositoryDescriptor;
    private final State state;
    private final LocalDateInterval updatedOn;

    /**
     * Creeates an instance of this class.
     * @param repositoryDescriptor
     * @param state
     * @param updatedOn
     */
    public PullRequestsRequest(RepositoryDescriptor repositoryDescriptor, State state, LocalDateInterval updatedOn) {
        if (repositoryDescriptor == null) {
            throw new IllegalArgumentException("repositoryDescriptor cannot be null");
        }

        this.repositoryDescriptor = repositoryDescriptor;
        this.state                = state;
        this.updatedOn            = updatedOn;
    }

    public PullRequestsRequest(RepositoryDescriptor repositoryDescriptor) {
        this(repositoryDescriptor, null, null);
    }

    public PullRequestsRequest(RepositoryDescriptor repositoryDescriptor, State state) {
        this(repositoryDescriptor, state, null);
    }

    public PullRequestsRequest(RepositoryDescriptor repositoryDescriptor, LocalDateInterval updatedOn) {
        this(repositoryDescriptor, null, updatedOn);
    }

    private static String formatDate(LocalDate date) {
        return date.toString();
    }

    @Override
    public String toString() {
        URLStringBuilder result       = new URLStringBuilder();
        URLQueryWriter urlQueryWriter = new URLQueryWriter(result);

        result.append("repositories/").append(repositoryDescriptor.toString()).append("/pullrequests");
        if (state != null || updatedOn != null) {
            result.append("?q=");
            appendState(urlQueryWriter);
            appendUpdatedOn(urlQueryWriter);
        }

        return result.toString();
    }

    private void appendUpdatedOn(URLQueryWriter urlQueryWriter) {
        if (updatedOn != null) {
            if (updatedOn.getStart() != null) {
                urlQueryWriter.write("updated_on", ">=", formatDate(updatedOn.getStart()));
            }

            if (updatedOn.getEnd() != null) {
                urlQueryWriter.write("updated_on", "<", formatDate(updatedOn.getEnd()));
            }
        }
    }

    private void appendState(URLQueryWriter urlQueryWriter) {
        if (state != null) {
            urlQueryWriter.write("state", "=", "\"" + state.toString().toUpperCase() + "\"");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PullRequestsRequest that = (PullRequestsRequest) o;

        if (!repositoryDescriptor.equals(that.repositoryDescriptor)) {
            return false;
        }

        if (state != that.state) {
            return false;
        }

        return updatedOn != null ? updatedOn.equals(that.updatedOn) : that.updatedOn == null;
    }

    @Override
    public int hashCode() {
        int result = repositoryDescriptor.hashCode();
        result     = 31 * result + (state != null ? state.hashCode() : 0);
        result     = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

    /**
     * State of a PR.
     */
    public enum State {
        /**
         * PR is open.
         */
        Open,

        /**
         * PR is merged.
         */
        Merged,

        /**
         * PR is declined.
         */
        Declined
    }
}
