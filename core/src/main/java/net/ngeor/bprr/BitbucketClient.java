package net.ngeor.bprr;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import net.ngeor.bitbucket.Repositories;
import net.ngeor.bitbucket.Repository;
import net.ngeor.http.JsonHttpClient;

import static net.ngeor.bprr.PageCollector.collectAll;

/**
 * A Bitbucket client.
 */
public class BitbucketClient {
    private final JsonHttpClient jsonHttpClient;
    private final String owner;

    public BitbucketClient(JsonHttpClient jsonHttpClient, String owner) {
        this.jsonHttpClient = jsonHttpClient;
        this.owner          = owner;
    }

    /**
     * Gets all repositories.
     *
     * @return
     * @throws IOException
     */
    public List<Repository> getAllRepositories() throws IOException {
        return collectAll(
            jsonHttpClient,
            jsonHttpClient.read("https://api.bitbucket.org/2.0/repositories/" + owner, Repositories.class),
            Repositories.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitbucketClient that = (BitbucketClient) o;
        return jsonHttpClient.equals(that.jsonHttpClient) && owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonHttpClient, owner);
    }
}
