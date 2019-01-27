package net.ngeor.bitbucket;

/**
 * Links of a pull request or other resource.
 */
public class Links {
    private Link self;

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public Links self(Link self) {
        this.self = self;
        return this;
    }
}
