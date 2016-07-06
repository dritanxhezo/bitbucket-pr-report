package net.ngeor.bprr;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a consumer of an InputStream.
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
