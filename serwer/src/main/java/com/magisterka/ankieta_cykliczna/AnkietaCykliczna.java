package com.magisterka.ankieta_cykliczna;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

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
    @Nullable
    private List<UwagaZdrowotna> uwagiZdrowotne;
    @Nullable
    private String inneUwagiZdrowotne;
    private Instant createdAt;

    @NotNull
    public List<UwagaZdrowotna> getUwagiZdrowotne() {
        if (uwagiZdrowotne == null) {
            return Collections.emptyList();
        }
        return uwagiZdrowotne;
    }
}
