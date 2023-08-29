package com.magisterka.ankieta_cykliczna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String samopoczucie;
    private String uwagiZdrowotne;
    private String inneUwagiZdrowotne;
    private Instant createdAt;
}
