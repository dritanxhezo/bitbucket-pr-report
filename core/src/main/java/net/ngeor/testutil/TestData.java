package net.ngeor.testutil;

import java.io.IOException;
import java.io.InputStream;

import net.ngeor.json.ObjectMapperFactory;

/**
 * Helper for test data.
 */
public final class TestData {
    private static final TestData INSTANCE = new TestData();

    private TestData() {
    }

    public static <T> T load(Class<T> type, String suffix) throws IOException {
        String name = '/' + type.getName().replace('.', '/') + suffix + ".json";
        return INSTANCE.fromTestData(name, type);
    }

    private <T> T fromTestData(String name, Class<T> type) throws IOException {
        InputStream responseStream = getClass().getResourceAsStream(name);
        if (responseStream == null) {
            throw new NullPointerException("Could not load resource " + name);
        }

        return ObjectMapperFactory.create().readValue(responseStream, type);
    }
}
