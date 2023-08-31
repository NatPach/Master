package com.magisterka.ankieta_cykliczna;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.List;

@Secured(SecurityRoles.DOCTOR)
@Controller
public class DoctorAnkietaCyklicznaController {
    @Inject
    private AnkietaCyklicznaRepository ankietaCyklicznaRepository;

    @Get("/patients/{patientId}/ankieta-cykliczna")
    public List<AnkietaCykliczna> getPatientAnkietaCykliczna(@PathVariable @NotNull Long patientId) {
        return ankietaCyklicznaRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(this::mapFromEntity)
                .toList();
    }

    private AnkietaCykliczna mapFromEntity(AnkietaCyklicznaEntity entity) {
        return new AnkietaCykliczna(
                entity.getTetno(),
                entity.getSamopoczucie(),
                entity.getWaga(),
                entity.isPotrzebaWizyty(),
                entity.getInneUwagiZdrowotne(),
                entity.getCreatedAt());
    }
}
