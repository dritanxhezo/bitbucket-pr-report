package com.github.ngeor.web2.services;

import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ngeor.web2.db.RepositoryRepository;

/**
 * Integration tests for {@link PullRequestService}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PullRequestServiceIT {
    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private PullRequestService pullRequestService;

    @Test
    void saveAll() {
        StreamSupport.stream(repositoryRepository.findAll().spliterator(), false).forEach(pullRequestService::saveAll);
    }
}
