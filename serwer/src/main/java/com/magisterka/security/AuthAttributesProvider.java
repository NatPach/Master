package com.magisterka.security;

import com.magisterka.doctor.DoctorAuthProvider;
import com.magisterka.patient.PatientAuthProvider;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthAttributesProvider {
    @Inject
    private SecurityService securityService;

    public Long getDoctorId() {
        return (Long) securityService.getAuthentication()
                .orElseThrow()
                .getAttributes()
                .get(DoctorAuthProvider.DOCTOR_ID_ATTRIBUTE);
    }

    public Long getPatientId() {
        return (Long) securityService.getAuthentication()
                .orElseThrow()
                .getAttributes()
                .get(PatientAuthProvider.PATIENT_ID_ATTRIBUTE);
    }
}
