package com.magisterka.ankieta_cykliczna;

import com.magisterka.potrzeba_wizyty.PotrzebaWizytyService;
import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Secured(SecurityRoles.PATIENT)
@Controller("/ankieta-cykliczna")
public class PatientAnkietaCyklicznaController {
    @Inject
    private AuthAttributesProvider authAttributesProvider;
    @Inject
    private AnkietaCyklicznaRepository ankietaCyklicznaRepository;
    @Inject
    private PotrzebaWizytyService potrzebaWizytyService;

    @Get
    public List<AnkietaCykliczna> getPatientAnkietaCykliczna() {
        long patientId = authAttributesProvider.getPatientId();
        return ankietaCyklicznaRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(this::mapFromEntity)
                .toList();
    }

    @Post
    public void putPatientAnkietaCykliczna(@NotNull @Valid @Body AnkietaCykliczna ankietaCykliczna) {
        long patientId = authAttributesProvider.getPatientId();
        Instant createdAt = Instant.now();
        AnkietaCyklicznaEntity entity = ankietaCyklicznaRepository.save(new AnkietaCyklicznaEntity(
                null,
                patientId,
                ankietaCykliczna.getTetno(),
                ankietaCykliczna.getSamopoczucie(),
                ankietaCykliczna.getWaga(),
                ankietaCykliczna.isPotrzebaWizyty(),
                ankietaCykliczna.getInneUwagiZdrowotne(),
                createdAt));
        potrzebaWizytyService.stworzPotrzebeWizytyJesliPotrzeba(entity);
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
