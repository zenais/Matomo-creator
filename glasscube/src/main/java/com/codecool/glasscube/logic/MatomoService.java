package com.codecool.glasscube.logic;

import com.codecool.glasscube.persistance.matomo.Matomo;
import com.codecool.glasscube.persistance.matomo.MatomoRepository;
import com.codecool.glasscube.persistance.metadata.Metadata;
import com.codecool.glasscube.persistance.metadata.MetadataRepository;
import com.codecool.glasscube.persistance.spec.Spec;
import com.codecool.glasscube.persistance.spec.SpecRepository;
import com.codecool.glasscube.validator.MetadataValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatomoService {
    private final MatomoRepository matomoRepository;
    private final MetadataRepository metadataRepository;
    private final SpecRepository specRepository;
    private final ObjectMapper yamlMapper;
    private final MetadataValidator metadataValidator;
    private final String apiVersion;
    private final String kind;

    public MatomoService(
            MatomoRepository matomoRepository,
            MetadataRepository metadataRepository,
            SpecRepository specRepository,
            ObjectMapper yamlMapper,
            MetadataValidator metadataValidator,
            @Value("${glasscube.apiVersion}") String apiVersion,
            @Value("${glasscube.kind}") String kind) {
        this.matomoRepository = matomoRepository;
        this.metadataRepository = metadataRepository;
        this.specRepository = specRepository;
        this.yamlMapper = yamlMapper;
        this.metadataValidator = metadataValidator;
        this.apiVersion = apiVersion;
        this.kind = kind;
    }

    public ResponseEntity<Matomo> save(Matomo matomo) {
        if (metadataValidator.validate(matomo.getMetadata())) {
            return ResponseEntity.ok(matomoRepository.save(createMatomo(matomo)));
        }
        return ResponseEntity.badRequest().build();
    }

    public List<Matomo> findAll() {
        return matomoRepository.findAll();
    }

    public Optional<String> getMatomoYml(Matomo matomo) {
        return findByMetadata(matomo).map(this::getYaml);
    }

    private Matomo createMatomo(Matomo matomo) {
        Metadata metadata = metadataRepository.save(matomo.getMetadata());
        Spec spec = specRepository.save(matomo.getSpec());
        Matomo matomoNew = new Matomo();
        matomoNew.setMetadata(metadata);
        matomoNew.setSpec(spec);
        matomoNew.setApiVersion(apiVersion);
        matomoNew.setKind(kind);
        return matomoNew;
    }

    private String getYaml(Matomo matomoData) {
        try {
            return yamlMapper.writeValueAsString(matomoData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Matomo> findByMetadata(Matomo matomo) {
        Optional<Metadata> metadata = metadataRepository.findByNameAndNamespace(
                matomo.getMetadata().getName(),
                matomo.getMetadata().getNamespace());
        return metadata.flatMap(matomoRepository::findByMetadata);
    }
}
