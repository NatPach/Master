package com.magisterka.potrzeba_wizyty;

import com.magisterka.patient.PatientRepository;
import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import java.util.List;

@Secured(SecurityRoles.DOCTOR)
@Controller
public class PotrzebaWizytyController {
    @Inject
    private PotrzebaWizytyService service;
    @Inject
    private AuthAttributesProvider authAttributesProvider;
    @Inject
    private PatientRepository patientRepository;

    @Get("/potrzeby-wizyty")
    public List<PotrzebaWizyty> getPotrzebyWizyty() {
        Long doctorId = authAttributesProvider.getDoctorId();
        return service.getListaPacjentowPotrzebujacychWizyty(doctorId)
                .stream()
                .map(patientId -> new PotrzebaWizyty(null, null, patientId))
                .toList();
    }

    @Delete("/patients/{patientId}/potrzeby-wizyty")
    @Status(HttpStatus.NO_CONTENT)
    public void clearPotrzebyWizytyDlaPacjenta(@PathVariable Long patientId) {
        Long doctorId = authAttributesProvider.getDoctorId();
        patientRepository.findById(patientId)
                .filter(patient -> doctorId.equals(patient.getLekarzProwadzacyId()))
                .ifPresent(patient -> service.clearPotrzebyWizytyDlaPacjenta(doctorId, patient.getId()));
    }
}
