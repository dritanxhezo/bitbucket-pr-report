package net.ngeor.bprr;

public class RepositoriesResponse {
    private Repository[] values;

    public Repository[] getValues() {
        return values;
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

    public class Link {
        private String href;

        public String getHref() {
            return href;
        }
    }

}