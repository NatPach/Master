package com.magisterka.ankieta_wstepna;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Introspected
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnkietaWstepna {
    @NotNull
    @Positive
    private Double waga;
    @NotNull
    @Positive
    private Double wzrost;
    @NotBlank
    private BloodType grupaKrwi;
    @NotBlank
    private TrybZycia trybZycia;
    @Nullable
    private String przyjmowaneLeki;
    @Nullable
    private String alergie;
}
