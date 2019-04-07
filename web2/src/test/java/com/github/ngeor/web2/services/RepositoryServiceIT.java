package com.github.ngeor.web2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Integration tests for {@link RepositoryService}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RepositoryServiceIT {
    @Autowired
    private RepositoryService repositoryService;

    @Test
    void saveAll() {
        repositoryService.saveAll();
    }
}
