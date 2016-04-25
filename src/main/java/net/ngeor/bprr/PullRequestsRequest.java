package net.ngeor.bprr;

public class PullRequestsRequest {
    private final String owner;
    private final String repositorySlug;

    public PullRequestsRequest(String owner, String repositorySlug) {
        this.owner = owner;
        this.repositorySlug = repositorySlug;
    }

    @Override
    public String toString() {
        return "repositories/" + owner + "/" + repositorySlug + "/pullrequests";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestsRequest that = (PullRequestsRequest) o;

        if (!owner.equals(that.owner)) return false;
        return repositorySlug.equals(that.repositorySlug);

    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + repositorySlug.hashCode();
        return result;
    }
}
