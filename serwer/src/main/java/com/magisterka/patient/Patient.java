package com.magisterka.patient;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Introspected
@Data
@Builder
public class Patient {
    private Long id;
    @NotBlank
    private String firstName;
    private String middleName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotBlank
    private String pesel;
}
