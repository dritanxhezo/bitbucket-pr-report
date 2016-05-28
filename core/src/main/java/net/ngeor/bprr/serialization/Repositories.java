package net.ngeor.bprr.serialization;

public class Repositories {
    private Repository[] values;
    private int page;
    private int size;
    private int pagelen;
    private String next;

    /**
     * Gets the URL to the next page of the response.
     */
    public String getNext() {
        return next;
    }

    public Repository[] getValues() {
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

    public class Repository {
        private String name;
        private Links links;
        private String full_name;

        public String getName() {
            return name;
        }

        public Links getLinks() {
            return links;
        }

        public String getFullName() {
            return full_name;
        }
    }

    public class Links {
        private Link pullrequests;

        public Link getPullRequests() {
            return pullrequests;
        }
    }
}