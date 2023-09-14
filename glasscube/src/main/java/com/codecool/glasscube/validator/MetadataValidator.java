package com.codecool.glasscube.validator;

import com.codecool.glasscube.persistance.matomo.MatomoRepository;
import com.codecool.glasscube.persistance.metadata.Metadata;
import com.codecool.glasscube.persistance.metadata.MetadataRepository;
import org.springframework.stereotype.Component;

@Component
public class MetadataValidator {
    private final MetadataRepository metadataRepository;

    public MetadataValidator(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public boolean validate(Metadata metadata){
        return !(metadataRepository.existsByName(metadata.getName()) ||
                metadataRepository.existsByNamespace(metadata.getNamespace()));
    }
}
