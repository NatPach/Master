package com.magisterka.ankieta_cykliczna;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Secured(SecurityRoles.DOCTOR)
@Controller
public class DoctorAnkietaCyklicznaController {
    @Inject
    private AnkietaCyklicznaRepository ankietaCyklicznaRepository;

    @Get("/patients/{patientId}/ankieta-cykliczna")
    public List<AnkietaCykliczna> getPatientAnkietaCykliczna(@PathVariable @NotNull Long patientId) {
        return ankietaCyklicznaRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapFromEntity)
                .toList();
    }

    private AnkietaCykliczna mapFromEntity(AnkietaCyklicznaEntity entity) {
        return new AnkietaCykliczna(
                entity.getTetno(),
                entity.getSamopoczucie(),
                getUwagiZdrowotne(entity.getUwagiZdrowotne()),
                entity.getInneUwagiZdrowotne(),
                entity.getCreatedAt());
    }

    private List<UwagaZdrowotna> getUwagiZdrowotne(String uwagiZdrowotne) {
        if (uwagiZdrowotne == null || uwagiZdrowotne.isBlank()) {
            return Collections.emptyList();
        }
        return Stream.of(uwagiZdrowotne.split(","))
                .map(UwagaZdrowotna::valueOf)
                .toList();
    }
}
