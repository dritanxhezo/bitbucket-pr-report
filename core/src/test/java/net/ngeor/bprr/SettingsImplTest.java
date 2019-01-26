package net.ngeor.bprr;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link SettingsImpl}.
 */
class SettingsImplTest {
    @Test
    void shouldLoadSettings() throws IOException {
        ResourceLoader resourceLoader = new ResourceLoaderImpl();
        SettingsImpl settings         = new SettingsImpl(resourceLoader);

        assertEquals("acme", settings.getOwner());
        assertEquals("myuser", settings.getUsername());
        assertEquals("asecret", settings.getPassword());
    }

    @Test
    void shouldAcceptSettingsAsParameters() {
        SettingsImpl settings = new SettingsImpl("owner", "user", "secret");

        assertThat(settings.getOwner()).isEqualTo("owner");
        assertEquals("user", settings.getUsername());
        assertEquals("secret", settings.getPassword());
    }
}
