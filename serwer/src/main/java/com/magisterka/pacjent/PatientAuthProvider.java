package com.magisterka.pacjent;

import com.magisterka.security.PasswordEncoder;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class PatientAuthProvider {
    @Inject
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticatePatient(String username, String password) {
        return AuthenticationResponse.failure("Jeszcze nie zaimplementowane");
    }
}
