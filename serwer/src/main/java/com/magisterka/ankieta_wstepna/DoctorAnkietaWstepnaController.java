package com.magisterka.ankieta_wstepna;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Secured(SecurityRoles.DOCTOR)
@Controller
public class DoctorAnkietaWstepnaController {
    @Inject
    private AnkietaWstepnaRepository ankietaWstepnaRepository;

    @Get("/doctor/patients/{patientId}/ankieta-wstepna")
    public Optional<AnkietaWstepna> getPatientAnkietaWstepna(@NotNull @PathVariable Long patientId) {
        return ankietaWstepnaRepository.findByPatientId(patientId)
                .map(entity -> new AnkietaWstepna(entity.getWeight(), entity.getHeight(), entity.getBloodType()));
    }
}
