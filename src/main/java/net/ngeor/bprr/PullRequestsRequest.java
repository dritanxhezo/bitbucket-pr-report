package net.ngeor.bprr;

public class PullRequestsRequest {
    private String owner;
    private String repositorySlug;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepositorySlug() {
        return repositorySlug;
    }

    public void setRepositorySlug(String repositorySlug) {
        this.repositorySlug = repositorySlug;
    }

    @Override
    public String toString() {
        return "repositories/" + owner + "/" + repositorySlug + "/pullrequests";
    }
}
