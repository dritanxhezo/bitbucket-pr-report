package net.ngeor.bprr;

public class PullRequestResponseBuilder {
    int id;

    public PullRequestResponse build() {
        return new PullRequestResponse(this);
    }

    public PullRequestResponseBuilder withId(int id) {
        this.id = id;
        return this;
    }
}
