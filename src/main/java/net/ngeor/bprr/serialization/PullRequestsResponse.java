package net.ngeor.bprr.serialization;

public class PullRequestsResponse {
    private int page;
    private int size;
    private int pagelen;
    private String next;
    private PullRequestResponse[] values;

    public PullRequestsResponse() {
        this(new PullRequestResponse[0]);
    }

    public PullRequestsResponse(PullRequestResponse... values) {
        this.values = values;
    }

    /**
     * Gets the URL to the next page of the response.
     */
    public String getNext() {
        return next;
    }

    public PullRequestResponse[] getValues() {
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
