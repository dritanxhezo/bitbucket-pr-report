package com.github.ngeor.web2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ngeor.bitbucket.BitbucketClient;
import com.github.ngeor.web2.db.PipelineRepository;
import com.github.ngeor.web2.db.PullRequestApproverRepository;
import com.github.ngeor.web2.db.PullRequestRepository;
import com.github.ngeor.web2.db.RepositoryRepository;
import com.github.ngeor.web2.db.UserRepository;
import com.github.ngeor.web2.mapping.EntityMapper;

/**
 * Integration test for {@link Application}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationIT {

    @Autowired
    private BitbucketClient bitbucketClient;

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private PullRequestApproverRepository pullRequestApproverRepository;

    @Test
    void contextLoads() {
    }
}
