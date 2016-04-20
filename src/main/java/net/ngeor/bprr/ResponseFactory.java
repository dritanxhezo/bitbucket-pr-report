package net.ngeor.bprr;

import java.io.Reader;

public interface ResponseFactory<E> {
    E parse(Reader reader);
}
