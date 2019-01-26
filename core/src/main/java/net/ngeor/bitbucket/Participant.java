package net.ngeor.bitbucket;

/**
 * Represents a participant in a PR.
 */
public class Participant {
    private boolean approved;
    private String role;
    private Author user;

    public Participant() {
    }

    /**
     * Creates an instance of this class.
     * @param approved
     * @param role
     * @param user
     */
    public Participant(boolean approved, String role, Author user) {
        this.approved = approved;
        this.role     = role;
        this.user     = user;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getRole() {
        return role;
    }

    public Author getUser() {
        return user;
    }
}
