package com.magisterka.ankieta_wstepna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity(name = "ankieta_wstepna")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnkietaWstepnaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long patientId;

    @Positive
    @NotNull
    private Double height;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BloodType bloodType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TrybZycia trybZycia;

    private String przyjmowaneLeki;

    private String alergie;
}
