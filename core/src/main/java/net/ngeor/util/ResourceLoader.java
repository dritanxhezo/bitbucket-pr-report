package net.ngeor.util;

import java.io.InputStream;

/**
 * Resource loader abstraction.
 */
public interface ResourceLoader { InputStream getResourceAsStream(String resourceId); }
