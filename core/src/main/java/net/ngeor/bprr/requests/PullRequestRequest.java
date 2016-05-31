package net.ngeor.bprr.requests;

import net.ngeor.bprr.RepositoryDescriptor;

public class PullRequestRequest {
    private final RepositoryDescriptor repositoryDescriptor;
    private final int id;

    public PullRequestRequest(RepositoryDescriptor repositoryDescriptor, int id) {
        this.repositoryDescriptor = repositoryDescriptor;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestRequest that = (PullRequestRequest) o;

        if (id != that.id) return false;
        return repositoryDescriptor.equals(that.repositoryDescriptor);

    }

    @Override
    public int hashCode() {
        int result = repositoryDescriptor.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "repositories/" + repositoryDescriptor + "/pullrequests/" + id;
    }
}
