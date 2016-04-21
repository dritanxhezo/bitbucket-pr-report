package net.ngeor.bprr;

public class PullRequestsResponse {
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

    public class PullRequest {
        private String description;
        private Author author;
        private String id;

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public Author getAuthor() {
            return author;
        }
    }

    public class Author {
        private String username;
        private String display_name;

        public String getUsername() {
            return username;
        }

        public String getDisplayName() {
            return display_name;
        }
    }
}
