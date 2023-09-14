package com.codecool.glasscube.persistance.matomo;

import com.codecool.glasscube.persistance.metadata.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatomoRepository extends JpaRepository<Matomo, String> {
    Optional<Matomo> findByMetadata(Metadata metadata);
}
