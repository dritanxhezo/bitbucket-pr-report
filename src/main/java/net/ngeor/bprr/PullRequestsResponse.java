package net.ngeor.bprr;

public class PullRequestsResponse {
    private int page;
    private int size;
    private int pagelen;
    private String next;
    private PullRequestResponse[] values;

    public PullRequestsResponse() {

    }

    PullRequestsResponse(PullRequestResponse... values) {
        this(null, values);
    }

    PullRequestsResponse(String next, PullRequestResponse... values) {
        this.next = next;
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

    void setValues(PullRequestResponse[] values) {
        this.values = values;
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
