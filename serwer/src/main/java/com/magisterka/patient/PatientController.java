package com.magisterka.patient;

import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.StreamSupport;

@Secured(SecurityRoles.DOCTOR)
@Controller
public class PatientController {
    @Inject
    private PatientRepository patientRepository;
    @Inject
    private AuthAttributesProvider authAttributesProvider;

    @Get("/patients")
    public List<Patient> getPatients() {
        Long doctorId = authAttributesProvider.getDoctorId();
        return StreamSupport.stream(patientRepository.findAllByLekarzProwadzacyId(doctorId).spliterator(), false)
                .map(this::mapFromEntity)
                .toList();
    }

    @Get("/patients/{patientId}")
    public Patient getPatient(@PathVariable @NotNull Long patientId) {
        Long doctorId = authAttributesProvider.getDoctorId();
        return patientRepository.findById(patientId)
                .filter(patient -> doctorId.equals(patient.getLekarzProwadzacyId()))
                .map(this::mapFromEntity)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Patient with id %s not found.".formatted(patientId)));
    }

    private Patient mapFromEntity(PatientEntity entity) {
        return Patient.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .pesel(entity.getPesel())
                .build();
    }
}
