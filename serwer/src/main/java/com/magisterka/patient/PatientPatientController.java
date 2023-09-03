package com.magisterka.patient;

import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.function.BiConsumer;

@Secured(SecurityRoles.PATIENT)
@Controller("/me")
public class PatientPatientController {
    @Inject
    private PatientRepository patientRepository;
    @Inject
    private AuthAttributesProvider authAttributesProvider;

    @Get
    public Patient getPatient() {
        Long patientId = authAttributesProvider.getPatientId();
        return patientRepository.findById(patientId)
                .map(this::mapFromEntity)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Patient with id %s not found.".formatted(patientId)));
    }

    @Put
    @Status(HttpStatus.NO_CONTENT)
    @Transactional
    public void updatePatient(@NotNull @Valid @Body Patient patient) {
        Long patientId = authAttributesProvider.getPatientId();
        PatientEntity entity = patientRepository.findById(patientId).orElseThrow();
        updateIfNotEmpty(patient.getFirstName(), entity, PatientEntity::setFirstName);
        updateIfNotEmpty(patient.getMiddleName(), entity, PatientEntity::setMiddleName);
        updateIfNotEmpty(patient.getLastName(), entity, PatientEntity::setLastName);
        updateIfNotEmpty(patient.getPhone(), entity, PatientEntity::setPhone);
        updateIfNotEmpty(patient.getEmail(), entity, PatientEntity::setEmail);
        updateIfNotEmpty(patient.getPesel(), entity, PatientEntity::setPesel);
        patientRepository.update(entity);
    }

    private void updateIfNotEmpty(String value, PatientEntity entity, BiConsumer<PatientEntity, String> entitySetter) {
        if (value != null && !value.isBlank()) {
            entitySetter.accept(entity, value);
        }
    }

    private Patient mapFromEntity(PatientEntity entity) {
        return Patient.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .pesel(entity.getPesel())
                .build();
    }
}
