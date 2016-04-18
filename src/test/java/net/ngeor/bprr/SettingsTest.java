package net.ngeor.bprr;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;

public class SettingsTest {
    @Test
    public void shouldBeSingleton() throws IOException {
        Settings settings1 = Settings.getInstance();
        Settings settings2 = Settings.getInstance();
        Assert.assertSame(settings1, settings2);
    }
}
