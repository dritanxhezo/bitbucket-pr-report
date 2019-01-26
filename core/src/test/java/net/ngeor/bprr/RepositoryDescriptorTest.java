package net.ngeor.bprr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link RepositoryDescriptor}.
 */
public class RepositoryDescriptorTest {
    @Test
    public void shouldParse() {
        RepositoryDescriptor repositoryDescriptor = RepositoryDescriptor.parse("user/repo");
        assertNotNull(repositoryDescriptor);
        assertEquals("user", repositoryDescriptor.getRepositoryOwner());
        assertEquals("repo", repositoryDescriptor.getRepositorySlug());
    }
}
