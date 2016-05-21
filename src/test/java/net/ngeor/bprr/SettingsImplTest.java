package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SettingsImplTest {
    @Test
    public void shouldLoadSettings() throws IOException {
        ResourceLoader resourceLoader = new ResourceLoaderImpl();
        SettingsImpl settings = new SettingsImpl(resourceLoader);

        assertEquals("myuser", settings.getUser());
        assertEquals("mykey", settings.getClientId());
        assertEquals("asecret", settings.getSecret());
    }
}
