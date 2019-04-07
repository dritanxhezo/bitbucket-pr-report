package com.github.ngeor.web2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.ngeor.web2.db.RepositoryRepository;
import com.github.ngeor.web2.services.PipelineService;
import com.github.ngeor.web2.services.PullRequestService;
import com.github.ngeor.web2.services.RepositoryService;

/**
 * Periodically updates the database from Bitbucket REST API.
 */
@Component
public class ScheduledTasks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private PullRequestService pullRequestService;

    @Autowired
    private RepositoryRepository repositoryRepository;

    /**
     * Updates all data.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void updateAll() {
        LOGGER.info("Updating repositories");
        repositoryService.saveAll();
        LOGGER.info("Updating pipelines");
        repositoryRepository.findAll().forEach(pipelineService::saveAll);
        LOGGER.info("Updating pull requests");
        repositoryRepository.findAll().forEach(pullRequestService::saveAll);
    }
}
