package net.ngeor.bprr;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

public class TestData {
    private TestData() {
    }

    private static final TestData INSTANCE = new TestData();

    public static <T> T load(Class<T> type, String suffix) {
        String name = type.getSimpleName() + suffix + ".json";
        return INSTANCE.fromTestData(name, type);
    }

    private <T> T fromTestData(String name, Class<T> type) {
        InputStream responseStream = getClass().getResourceAsStream(name);
        InputStreamReader reader = new InputStreamReader(responseStream);
        Gson gson = new Gson();
        return gson.fromJson(reader, type);
    }
}
