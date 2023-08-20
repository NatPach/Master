package com.magisterka.ankieta_cykliczna;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
}
