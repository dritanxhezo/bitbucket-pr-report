package net.ngeor.bitbucket;

import java.util.List;

/**
 * Represents a Bitbucket response that supports pagination.
 * @param <V> The value of the items in the page.
 */
public interface Paginated<V> {
    /**
     * Gets the URL to fetch the next page of this result.
     * @return A URL.
     */
    String getNext();

    /**
     * Gets the values in this page.
     * @return The values.
     */
    List<V> getValues();
}
