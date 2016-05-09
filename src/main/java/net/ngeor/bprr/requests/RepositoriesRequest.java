package net.ngeor.bprr.requests;

public class RepositoriesRequest {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "repositories/" + user;
    }
}
