package com.magisterka.ankieta_wstepna;

import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Secured(SecurityRoles.PATIENT)
@Controller
public class PatientAnkietaWstepnaController {
    @Inject
    private AuthAttributesProvider authAttributesProvider;
    @Inject
    private AnkietaWstepnaRepository ankietaWstepnaRepository;

    @Get("/ankieta-wstepna")
    public Optional<AnkietaWstepna> getPatientInterview() {
        long patientId = authAttributesProvider.getPatientId();
        return ankietaWstepnaRepository.findByPatientId(patientId)
                .map(entity -> new AnkietaWstepna(entity.getWeight(), entity.getHeight(), entity.getBloodType()));
    }

    @Put("/ankieta-wstepna")
    public AnkietaWstepna putPatientInterview(@NotNull @Valid @Body AnkietaWstepna ankietaWstepna) {
        long patientId = authAttributesProvider.getPatientId();
        AnkietaWstepnaEntity entity = new AnkietaWstepnaEntity(null, patientId, ankietaWstepna.getWaga(), ankietaWstepna.getWzrost(), ankietaWstepna.getGrupaKrwi());
        return ankietaWstepnaRepository.findByPatientId(patientId)
                .map(existing -> {
                    entity.setId(existing.getId());
                    return ankietaWstepnaRepository.update(entity);
                })
                .or(() -> Optional.of(ankietaWstepnaRepository.save(entity)))
                .map(value -> new AnkietaWstepna(value.getWeight(), value.getHeight(), value.getBloodType()))
                .orElseThrow();
    }
}
