package net.ngeor.bprr;

public class Author {
    private String username;
    private String display_name;

    public Author() {

    }

    public Author(String username, String display_name) {
        this.username = username;
        this.display_name = display_name;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return display_name;
    }

}
