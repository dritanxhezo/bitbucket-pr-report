package net.ngeor.bprr;

import java.io.IOException;

public interface RestClient {
    <E> E execute(Object resource, Class<E> responseType) throws IOException;
}
