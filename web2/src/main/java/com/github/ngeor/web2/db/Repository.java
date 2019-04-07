package com.github.ngeor.web2.db;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Repository entity.
 */
@Entity
public class Repository {
    @Id
    private String uuid;

    private String slug;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
