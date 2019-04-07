package com.github.ngeor.web2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ngeor.bitbucket.BitbucketClient;
import com.github.ngeor.bitbucket.models.Participant;
import com.github.ngeor.web2.db.PullRequest;
import com.github.ngeor.web2.db.PullRequestApprover;
import com.github.ngeor.web2.db.PullRequestApproverId;
import com.github.ngeor.web2.db.PullRequestApproverRepository;
import com.github.ngeor.web2.db.PullRequestId;
import com.github.ngeor.web2.db.PullRequestRepository;
import com.github.ngeor.web2.db.Repository;
import com.github.ngeor.web2.db.User;
import com.github.ngeor.web2.db.UserRepository;
import com.github.ngeor.web2.mapping.EntityMapper;

/**
 * Pull request service.
 */
@Service
public class PullRequestService {
    @Autowired
    private BitbucketClient bitbucketClient;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private PullRequestApproverRepository pullRequestApproverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsernameNormalizer usernameNormalizer;

    private User saveUser(com.github.ngeor.bitbucket.models.User userModel) {
        User user = entityMapper.toEntity(userModel);
        user.setUsername(usernameNormalizer.normalize(user.getUsername()));
        return userRepository.save(user);
    }

    /**
     * Save all pull requests of a repository.
     */
    public void saveAll(Repository repository) {
        bitbucketClient.getAllPullRequests(repository.getSlug()).forEach(pr -> {
            saveUser(pr.getAuthor());

            PullRequestId pri = new PullRequestId();
            pri.setId(pr.getId());
            pri.setRepositoryUuid(repository.getUuid());

            PullRequest pullRequest = entityMapper.toEntity(pr);
            pullRequest.setRepository(repository);
            pullRequest.setId(pri);
            pullRequestRepository.save(pullRequest);

            pr.getParticipants()
                .stream()
                .filter(Participant::isApproved)
                .map(Participant::getUser)
                .map(approver -> {
                    saveUser(approver);
                    var result = new PullRequestApprover();
                    var rid    = new PullRequestApproverId();
                    rid.setApproverUuid(approver.getUuid());
                    rid.setPullRequestId(pullRequest.getId());
                    result.setId(rid);
                    return result;
                })
                .forEach(pullRequestApproverRepository::save);
        });
    }
}
