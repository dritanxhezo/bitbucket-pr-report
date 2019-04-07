package com.github.ngeor.web2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ngeor.bitbucket.BitbucketClient;
import com.github.ngeor.web2.db.Pipeline;
import com.github.ngeor.web2.db.PipelineRepository;
import com.github.ngeor.web2.db.Repository;
import com.github.ngeor.web2.db.User;
import com.github.ngeor.web2.db.UserRepository;
import com.github.ngeor.web2.mapping.EntityMapper;

/**
 * Pipeline service.
 */
@Service
public class PipelineService {
    @Autowired
    private BitbucketClient bitbucketClient;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsernameNormalizer usernameNormalizer;

    /**
     * Save all pipelines of a repository.
     */
    public void saveAll(Repository repository) {
        bitbucketClient.getAllPipelines(repository.getSlug()).forEach(p -> {
            User creator = entityMapper.toEntity(p.getCreator());
            creator.setUsername(usernameNormalizer.normalize(creator.getUsername()));
            userRepository.save(creator);

            Pipeline pipeline = entityMapper.toEntity(p);
            pipeline.setRepository(repository);
            pipelineRepository.save(pipeline);
        });
    }
}
