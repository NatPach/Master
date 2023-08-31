package com.magisterka.ankieta_cykliczna;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Introspected
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnkietaCykliczna {
    @NotNull
    @Positive
    private Integer tetno;
    @NotNull
    private Samopoczucie samopoczucie;
    @NotNull
    @Positive
    private Double waga;
    private boolean potrzebaWizyty;
    @Nullable
    private String inneUwagiZdrowotne;
    private Instant createdAt;
}
