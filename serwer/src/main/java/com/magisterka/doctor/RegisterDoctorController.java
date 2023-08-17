package com.magisterka.doctor;

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
public class RegisterDoctorController {
    private final PasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;

    @Post("/doctor/register")
    public void registerDoctor(@NotNull @Valid @Body RegisterDoctorRequest request) {
        DoctorEntity entity = new DoctorEntity();
        entity.setEmail(request.getEmail());
        entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        doctorRepository.save(entity);
    }
}
