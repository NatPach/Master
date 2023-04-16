package com.magisterka.security;

import com.magisterka.lekarz.LekarzAuthProvider;
import com.magisterka.pacjent.PacjentAuthProvider;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Singleton
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Inject
    private LekarzAuthProvider lekarzAuthProvider;
    @Inject
    private PacjentAuthProvider pacjentAuthProvider;

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
        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        return lekarzAuthProvider.zalogujLekarza(username, password);
    }

    private AuthenticationResponse zalogujPacjenta(AuthenticationRequest<?, ?> authenticationRequest) {
        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        return pacjentAuthProvider.zalogujPacjenta(username, password);
    }
}
