package com.magisterka.lekarz;

import com.magisterka.security.PasswordEncoder;
import com.magisterka.security.SecurityRoles;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class LekarzAuthProvider {
    @Inject
    private LekarzRepository lekarzRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse zalogujLekarza(String username, String password) {
        return lekarzRepository.find(username)
                .filter(lekarz -> passwordEncoder.matches(password, lekarz.getPasswordHash()))
                .map(lekarz -> AuthenticationResponse.success(lekarz.getUsername(), List.of(SecurityRoles.LEKARZ)))
                .orElseGet(AuthenticationResponse::failure);
    }
}
