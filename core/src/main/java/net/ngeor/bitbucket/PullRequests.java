package net.ngeor.bitbucket;

import java.util.Arrays;
import java.util.List;

/**
 * A collection of pull requests.
 */
public class PullRequests implements Paginated<PullRequest> {
    private int page;
    private int size;
    private int pagelen;
    private String next;
    private List<PullRequest> values;

    public PullRequests() {
    }

    public PullRequests(PullRequest... pullRequests) {
        values = Arrays.asList(pullRequests);
    }

    @Override
    public String getNext() {
        return next;
    }

    @Override
    public List<PullRequest> getValues() {
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

    public void setNext(String next) {
        this.next = next;
    }
}
