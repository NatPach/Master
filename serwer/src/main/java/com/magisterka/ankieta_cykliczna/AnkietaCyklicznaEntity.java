package com.magisterka.ankieta_cykliczna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Entity(name = "ankieta_cykliczna")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnkietaCyklicznaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Integer tetno;
    @Enumerated(EnumType.STRING)
    private Samopoczucie samopoczucie;
    @Positive
    @NotNull
    private Double waga;
    private boolean potrzebaWizyty;
    private String inneUwagiZdrowotne;
    private Instant createdAt;
}
