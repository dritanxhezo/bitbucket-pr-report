package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a consumer of an InputStream.
 * @param <E> The type of the result.
 */
@FunctionalInterface
public interface InputStreamClient<E> {
    /**
     * Consumes the given InputStream.
     *
     * @param inputStream The InputStream to consume.
     * @return The result of the operation.
     * @throws IOException
     */
    E consume(InputStream inputStream) throws IOException;
}
