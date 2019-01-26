package net.ngeor.bprr;

/**
 * A repository descriptor.
 */
public class RepositoryDescriptor {
    private final String repositoryOwner;
    private final String repositorySlug;

    public RepositoryDescriptor(String repositoryOwner, String repositorySlug) {
        this.repositoryOwner = repositoryOwner;
        this.repositorySlug  = repositorySlug;
    }

    public static RepositoryDescriptor parse(String path) {
        String[] parts = path.split("/");
        return new RepositoryDescriptor(parts[0], parts[1]);
    }

    public String getRepositoryOwner() {
        return repositoryOwner;
    }

    public String getRepositorySlug() {
        return repositorySlug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RepositoryDescriptor that = (RepositoryDescriptor) o;

        if (!repositoryOwner.equals(that.repositoryOwner)) {
            return false;
        }

        return repositorySlug.equals(that.repositorySlug);
    }

    @Override
    public int hashCode() {
        int result = repositoryOwner.hashCode();
        result     = 31 * result + repositorySlug.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return repositoryOwner + "/" + repositorySlug;
    }
}
