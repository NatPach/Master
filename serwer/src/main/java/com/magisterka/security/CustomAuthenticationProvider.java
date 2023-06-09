package com.magisterka.security;

import com.magisterka.doctor.DoctorAuthProvider;
import com.magisterka.patient.PatientAuthProvider;
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
    private DoctorAuthProvider doctorAuthProvider;
    @Inject
    private PatientAuthProvider patientAuthProvider;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        if (httpRequest == null) {
            return Flux.just(AuthenticationResponse.failure("Request jest pusty."));
        }
        Optional<LoginSource> loginSource = httpRequest.getAttribute(LoginSource.REQUEST_ATTRIBUTE, LoginSource.class);
        if (loginSource.isEmpty()) {
            return Flux.just(AuthenticationResponse.failure("Brak zrodla logowania."));
        }
        switch (loginSource.get()) {
            case DOCTOR:
                return Flux.just(authenticateDoctor(authenticationRequest));
            case PATIENT:
                return Flux.just(authenticatePatient(authenticationRequest));
        }
        return Flux.just(AuthenticationResponse.failure("Niespodziewany blad."));
    }

    private AuthenticationResponse authenticateDoctor(AuthenticationRequest<?, ?> authenticationRequest) {
        String email = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        return doctorAuthProvider.authenticateDoctor(email, password);
    }

    private AuthenticationResponse authenticatePatient(AuthenticationRequest<?, ?> authenticationRequest) {
        String email = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        return patientAuthProvider.authenticatePatient(email, password);
    }
}
