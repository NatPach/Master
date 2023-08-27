package com.magisterka.ankieta_cykliczna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "ankieta_cykliczna")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnkietaCyklicznaEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long patientId;
    private Integer tetno;
    @Enumerated(EnumType.STRING)
    private Samopoczucie samopoczucie;
    private Instant createdAt;
}
