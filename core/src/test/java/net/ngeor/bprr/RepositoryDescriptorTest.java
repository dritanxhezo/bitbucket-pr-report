package net.ngeor.bprr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RepositoryDescriptorTest {
    @Test
    public void shouldParse() {
        RepositoryDescriptor repositoryDescriptor = RepositoryDescriptor.parse("user/repo");
        assertNotNull(repositoryDescriptor);
        assertEquals("user", repositoryDescriptor.getRepositoryOwner());
        assertEquals("repo", repositoryDescriptor.getRepositorySlug());
    }
}
