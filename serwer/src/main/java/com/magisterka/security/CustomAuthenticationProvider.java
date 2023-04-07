package com.magisterka.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Singleton
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        if (httpRequest == null) {
            return Flux.just(AuthenticationResponse.failure("Request jest pusty."));
        }
        Optional<ZrodloLogowania> zrodloLogowania = httpRequest.getAttribute(ZrodloLogowania.REQUEST_ATTRIBUTE, ZrodloLogowania.class);
        if (zrodloLogowania.isEmpty()) {
            return Flux.just(AuthenticationResponse.failure("Brak zrodla logowania."));
        }
        switch (zrodloLogowania.get()) {
            case LEKARZ:
                return Flux.just(zalogujLekarza(authenticationRequest));
            case PACJENT:
                return Flux.just(zalogujPacjenta(authenticationRequest));
        }
        return Flux.just(AuthenticationResponse.failure("Niespodziewany blad."));
    }

    private AuthenticationResponse zalogujLekarza(AuthenticationRequest<?, ?> authenticationRequest) {
        if (authenticationRequest.getIdentity().equals("lekarz") && authenticationRequest.getSecret().equals("password")) {
            return AuthenticationResponse.success("lekarz", List.of(SecurityRoles.LEKARZ));
        }
        return AuthenticationResponse.failure();
    }

    private AuthenticationResponse zalogujPacjenta(AuthenticationRequest<?, ?> authenticationRequest) {
        return AuthenticationResponse.failure("Jeszcze nie zaimplementowane");
    }
}
