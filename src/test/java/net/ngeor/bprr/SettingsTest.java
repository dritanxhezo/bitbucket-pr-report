package net.ngeor.bprr;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class SettingsTest {
    @Ignore
    @Test
    public void shouldBeSingleton() throws IOException {
        Settings settings1 = Settings.getInstance();
        Settings settings2 = Settings.getInstance();
        Assert.assertSame(settings1, settings2);
    }
}
