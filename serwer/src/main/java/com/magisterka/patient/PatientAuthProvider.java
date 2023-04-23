package com.magisterka.patient;

import com.magisterka.security.PasswordEncoder;
import com.magisterka.security.SecurityRoles;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;

@Singleton
public class PatientAuthProvider {
    public static final String PATIENT_ID_ATTRIBUTE = "patient_id";

    @Inject
    private PatientRepository patientRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticatePatient(String email, String password) {
        return patientRepository.find(email)
                .filter(patient -> passwordEncoder.matches(password, patient.getPasswordHash()))
                .map(this::getSuccessAuthResponse)
                .orElseGet(AuthenticationResponse::failure);
    }

    private AuthenticationResponse getSuccessAuthResponse(PatientEntity patient) {
        return AuthenticationResponse.success(
                patient.getEmail(), List.of(SecurityRoles.PATIENT), Map.of(PATIENT_ID_ATTRIBUTE, patient.getId()));
    }
}
