package com.magisterka.patient;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPatientRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @Nullable
    private String middleName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String pesel;
}
