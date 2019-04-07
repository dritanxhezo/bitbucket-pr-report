package com.github.ngeor.web2.db;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of (git) repositories.
 */
public interface RepositoryRepository extends JpaRepository<Repository, String> {}
