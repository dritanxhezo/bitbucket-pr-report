package net.ngeor.util;

import java.io.InputStream;

public interface ResourceLoader {
    InputStream getResourceAsStream(String resourceId);
}

