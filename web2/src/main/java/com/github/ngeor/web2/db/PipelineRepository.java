package com.github.ngeor.web2.db;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of pipelines.
 */
public interface PipelineRepository extends JpaRepository<Pipeline, String> {}
