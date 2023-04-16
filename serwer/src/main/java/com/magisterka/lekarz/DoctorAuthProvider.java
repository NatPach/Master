package com.magisterka.lekarz;

import com.magisterka.security.PasswordEncoder;
import com.magisterka.security.SecurityRoles;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class DoctorAuthProvider {
    @Inject
    private DoctorRepository doctorRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticateDoctor(String username, String password) {
        return doctorRepository.find(username)
                .filter(lekarz -> passwordEncoder.matches(password, lekarz.getPasswordHash()))
                .map(lekarz -> AuthenticationResponse.success(lekarz.getUsername(), List.of(SecurityRoles.DOCTOR)))
                .orElseGet(AuthenticationResponse::failure);
    }
}
