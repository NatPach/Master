package com.magisterka.patient;

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

    @Get("/patients")
    public List<Patient> getPatients() {
        return StreamSupport.stream(patientRepository.findAll().spliterator(), false)
                .map(this::mapFromEntity)
                .toList();
    }

    @Get("/patients/{patientId}")
    public Patient getPatient(@PathVariable @NotNull Long patientId) {
        return patientRepository.findById(patientId)
                .map(this::mapFromEntity)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Patient with id %s not found.".formatted(patientId)));
    }

    private Patient mapFromEntity(PatientEntity entity) {
        return Patient.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
}
