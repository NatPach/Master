package com.magisterka.potrzeba_wizyty;

import io.micronaut.core.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "potrzeba_wizyty")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PotrzebaWizytyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String context;

    @NotNull
    private Long patientId;

    @Nullable
    private String details;

    @Nullable
    private Instant closedAt;
}