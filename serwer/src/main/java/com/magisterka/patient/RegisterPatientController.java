package com.magisterka.patient;

import com.magisterka.security.PasswordEncoder;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
@AllArgsConstructor
public class RegisterPatientController {
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    @Post("/patient/register")
    public void registerPatient(@NotNull @Valid @Body RegisterPatientRequest request) {
        PatientEntity entity = new PatientEntity();
        entity.setEmail(request.getEmail());
        entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        patientRepository.save(entity);
    }
}
