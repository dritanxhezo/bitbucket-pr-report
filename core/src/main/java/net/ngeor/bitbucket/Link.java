package net.ngeor.bitbucket;

/**
 * Represents a link.
 */
public class Link {
    private String href;

    public Link() {
    }

    public Link(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
