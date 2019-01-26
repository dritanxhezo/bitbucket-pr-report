package net.ngeor.bprr;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link SettingsImpl}.
 */
public class SettingsImplTest {
    @Test
    public void shouldLoadSettings() throws IOException {
        ResourceLoader resourceLoader = new ResourceLoaderImpl();
        SettingsImpl settings         = new SettingsImpl(resourceLoader);

        assertEquals("myuser", settings.getUser());
        assertEquals("asecret", settings.getSecret());
    }

    @Test
    public void shouldAcceptSettingsAsParameters() {
        SettingsImpl settings = new SettingsImpl("user", "secret");

        assertEquals("user", settings.getUser());
        assertEquals("secret", settings.getSecret());
    }
}
