package com.magisterka.interview;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Introspected
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @NotNull
    @Positive
    private Double weight;
    @NotNull
    @Positive
    private Double height;
    @NotBlank
    private String bloodType;
}
