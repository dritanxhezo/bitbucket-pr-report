package net.ngeor.bprr.serialization;

public class Participant {
    private boolean approved;
    private String role;
    private Author user;

    public Participant() {
    }

    public Participant(boolean approved, String role, Author user) {
        this.approved = approved;
        this.role = role;
        this.user = user;
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
