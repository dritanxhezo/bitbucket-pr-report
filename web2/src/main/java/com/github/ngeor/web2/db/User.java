package com.github.ngeor.web2.db;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User entity.
 */
@Entity
public class User {
    @Id
    private String uuid;

    private String username;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
