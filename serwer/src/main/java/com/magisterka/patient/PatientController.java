package com.magisterka.patient;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.StreamSupport;

@Secured(SecurityRoles.DOCTOR)
@Controller
public class PatientController {
    @Inject
    private PatientRepository patientRepository;

    @Get("/patients")
    List<Patient> getPatients() {
        return StreamSupport.stream(patientRepository.findAll().spliterator(), false)
                .map(entity -> Patient.builder()
                        .id(entity.getId())
                        .email(entity.getEmail())
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .build())
                .toList();
    }
}
