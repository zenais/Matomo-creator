package com.codecool.glasscube.persistance.matomo;

import com.codecool.glasscube.persistance.metadata.Metadata;
import com.codecool.glasscube.persistance.spec.Spec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Matomo {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String apiVersion;
    private String kind;
    @OneToOne
    private Metadata metadata;
    @OneToOne
    private Spec spec;
}
