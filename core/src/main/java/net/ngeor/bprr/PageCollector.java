package net.ngeor.bprr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import net.ngeor.bitbucket.Paginated;
import net.ngeor.http.JsonHttpClient;

/**
 * Utility class to collect all pages of a paginated result.
 */
final class PageCollector {
    private PageCollector() {
    }

    /**
     * Collects all pages of a paginated result.
     */
    static <E extends Paginated<V>, V> List<V>
    collectAll(JsonHttpClient jsonHttpClient, E firstResponse, Class<E> firstResponseClass) throws IOException {
        List<V> result = new ArrayList<>(firstResponse.getValues());

        String next = firstResponse.getNext();
        while (!StringUtils.isBlank(next)) {
            E nextResponse = jsonHttpClient.read(next, firstResponseClass);
            result.addAll(nextResponse.getValues());
            next = nextResponse.getNext();
        }

        return result;
    }
}
