package net.ngeor.bprr;

public class PullRequestResponse {
    private String description;
    private PullRequestsResponse.Author author;
    private String id;
    private Participant[] participants;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public PullRequestsResponse.Author getAuthor() {
        return author;
    }

    public Participant[] getParticipants() {
        return participants;
    }

    public class Participant {
        private boolean approved;
        private String role;
        private PullRequestsResponse.Author user;

        public boolean isApproved() {
            return approved;
        }

        public String getRole() {
            return role;
        }

        public PullRequestsResponse.Author getUser() {
            return user;
        }
    }
}
