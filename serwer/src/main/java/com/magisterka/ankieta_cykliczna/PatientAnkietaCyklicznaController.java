package com.magisterka.ankieta_cykliczna;

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
import java.util.List;

@Secured(SecurityRoles.PATIENT)
@Controller("/ankieta-cykliczna")
public class PatientAnkietaCyklicznaController {
    @Inject
    private AuthAttributesProvider authAttributesProvider;
    @Inject
    private AnkietaCyklicznaRepository ankietaCyklicznaRepository;

    @Get
    public List<AnkietaCykliczna> getPatientAnkietaCykliczna() {
        long patientId = authAttributesProvider.getPatientId();
        return ankietaCyklicznaRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapFromEntity)
                .toList();
    }

    @Post
    public void putPatientAnkietaCykliczna(@NotNull @Valid @Body AnkietaCykliczna ankietaCykliczna) {
        long patientId = authAttributesProvider.getPatientId();
        AnkietaCyklicznaEntity entity = new AnkietaCyklicznaEntity(null, patientId, ankietaCykliczna.getTetno(), ankietaCykliczna.getSamopoczucie());
        ankietaCyklicznaRepository.save(entity);
    }

    private AnkietaCykliczna mapFromEntity(AnkietaCyklicznaEntity entity) {
        return new AnkietaCykliczna(entity.getTetno(), entity.getSamopoczucie());
    }
}
