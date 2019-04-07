package com.github.ngeor.web2.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.github.ngeor.web2.db.Pipeline;
import com.github.ngeor.web2.db.PullRequest;
import com.github.ngeor.web2.db.Repository;
import com.github.ngeor.web2.db.User;

/**
 * Mapper between entity and model.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EntityMapper {
    Repository toEntity(com.github.ngeor.bitbucket.models.Repository model);

    @Mappings({
        @Mapping(target = "state", source = "state.name")
        , @Mapping(target = "repository", ignore = true), @Mapping(target = "triggerName", source = "trigger.name"),
            @Mapping(target = "targetRefName", source = "target.refName"),
            @Mapping(target = "result", source = "state.result.name")
    })
    Pipeline
    toEntity(com.github.ngeor.bitbucket.models.Pipeline model);

    User toEntity(com.github.ngeor.bitbucket.models.User model);

    @Mappings({
        @Mapping(target = "repository", ignore = true)
        , @Mapping(target = "id.id", source = "id")
    })
    PullRequest
    toEntity(com.github.ngeor.bitbucket.models.PullRequest model);
}
