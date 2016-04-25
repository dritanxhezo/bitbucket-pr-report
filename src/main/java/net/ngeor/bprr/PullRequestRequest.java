package net.ngeor.bprr;

public class PullRequestRequest {
    private final String owner;
    private final String repositorySlug;
    private final int id;

    public PullRequestRequest(String owner, String repositorySlug, int id) {
        this.owner = owner;
        this.repositorySlug = repositorySlug;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequestRequest that = (PullRequestRequest) o;

        if (id != that.id) return false;
        if (!owner.equals(that.owner)) return false;
        return repositorySlug.equals(that.repositorySlug);

    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + repositorySlug.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "repositories/" + owner + "/" + repositorySlug + "/pullrequests/" + id;
    }
}
