package com.codecool.glasscube.persistance.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetadataRepository extends JpaRepository<Metadata, String> {
    Optional<Metadata> findByNameAndNamespace(String name, String namespace);

    boolean existsByName(String name);

    boolean existsByNamespace(String namespace);
}
