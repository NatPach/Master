package com.magisterka.lekarz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity(name = "lekarz")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LekarzEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String imie;

    @NotBlank
    private String nazwisko;

    @NotBlank
    private String username;

    @NotBlank
    private String passwordHash;
}
