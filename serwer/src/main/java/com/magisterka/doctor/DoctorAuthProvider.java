package com.magisterka.doctor;

import com.magisterka.security.PasswordEncoder;
import com.magisterka.security.SecurityRoles;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;

@Singleton
public class DoctorAuthProvider {
    public static final String DOCTOR_ID_ATTRIBUTE = "doctor_id";

    @Inject
    private DoctorRepository doctorRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticateDoctor(String email, String password) {
        return doctorRepository.find(email)
                .filter(doctor -> passwordEncoder.matches(password, doctor.getPasswordHash()))
                .map(this::getSuccessAuthResponse)
                .orElseGet(AuthenticationResponse::failure);
    }

    private AuthenticationResponse getSuccessAuthResponse(DoctorEntity doctor) {
        return AuthenticationResponse.success(
                doctor.getEmail(), List.of(SecurityRoles.DOCTOR), Map.of(DOCTOR_ID_ATTRIBUTE, doctor.getId()));
    }
}
