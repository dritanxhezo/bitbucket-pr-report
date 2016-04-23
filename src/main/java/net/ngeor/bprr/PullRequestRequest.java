package net.ngeor.bprr;

public class PullRequestRequest {
    private String owner;
    private String repositorySlug;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return "repositories/" + owner + "/" + repositorySlug + "/pullrequests/" + id;
    }
}
