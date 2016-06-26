package net.ngeor.bitbucket;

public class Author {
    private String username;
    private String display_name;

    public Author() {

    }

    public Author(String username, String displayName) {
        this.username = username;
        this.display_name = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return display_name;
    }

}
