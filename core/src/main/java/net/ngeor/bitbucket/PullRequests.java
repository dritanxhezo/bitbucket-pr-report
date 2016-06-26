package net.ngeor.bitbucket;

public class PullRequests {
    private int page;
    private int size;
    private int pagelen;
    private String next;
    private PullRequest[] values;

    /**
     * Gets the URL to the next page of the response.
     */
    public String getNext() {
        return next;
    }

    public PullRequest[] getValues() {
        return values;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getPageLen() {
        return pagelen;
    }
}
