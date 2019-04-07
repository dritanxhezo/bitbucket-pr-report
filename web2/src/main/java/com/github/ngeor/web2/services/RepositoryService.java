package com.github.ngeor.web2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ngeor.bitbucket.BitbucketClient;
import com.github.ngeor.web2.db.RepositoryRepository;
import com.github.ngeor.web2.mapping.EntityMapper;

/**
 * Saves all repositories.
 */
@Service
public class RepositoryService {
    @Autowired
    private BitbucketClient bitbucketClient;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private RepositoryRepository repositoryRepository;

    /**
     * Saves all repositories into the database.
     */
    public void saveAll() {
        bitbucketClient.getAllRepositories().map(entityMapper::toEntity).forEach(repositoryRepository::save);
    }
}
