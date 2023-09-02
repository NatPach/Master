package com.magisterka.patient;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @Nullable
    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String pesel;

    @NotBlank
    private String phone;

    // todo adres

    @NotBlank
    private String email;

    @NotBlank
    private String passwordHash;

    @Column(name = "lekarz_prowadzacy_id")
    @Nullable
    private Long lekarzProwadzacyId;
}
