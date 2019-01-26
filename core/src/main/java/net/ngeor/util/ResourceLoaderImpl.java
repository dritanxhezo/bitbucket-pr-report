package net.ngeor.util;

import java.io.InputStream;

/**
 * Implementation of {@link ResourceLoader}.
 */
public class ResourceLoaderImpl implements ResourceLoader {

    @Override
    public InputStream getResourceAsStream(String resourceId) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(resourceId);
    }
}
