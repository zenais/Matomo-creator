package com.codecool.glasscube.api.endpoint;

import com.codecool.glasscube.logic.MatomoService;
import com.codecool.glasscube.persistance.matomo.Matomo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1alpha/matomos")
public class MatomoEndpoint {
    private final MatomoService matomoService;

    public MatomoEndpoint(MatomoService matomoService) {
        this.matomoService = matomoService;
    }

    @GetMapping("all")
    public List<Matomo> findAll() {
        return matomoService.findAll();
    }

    @GetMapping
    public ResponseEntity<String> find(@RequestBody Matomo matomo) {
        return matomoService.getMatomoYml(matomo)
                .map(matomoYaml -> ResponseEntity.ok().body(matomoYaml))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Matomo> save(@RequestBody Matomo matomo) {
        return matomoService.save(matomo);
    }
}
