package com.magisterka.patient;

import com.magisterka.security.PasswordEncoder;
import com.magisterka.security.SecurityRoles;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class PatientAuthProvider {
    @Inject
    private PatientRepository patientRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticatePatient(String username, String password) {
        return patientRepository.find(username)
                .filter(doctor -> passwordEncoder.matches(password, doctor.getPasswordHash()))
                .map(doctor -> AuthenticationResponse.success(doctor.getUsername(), List.of(SecurityRoles.DOCTOR)))
                .orElseGet(AuthenticationResponse::failure);
    }
}
