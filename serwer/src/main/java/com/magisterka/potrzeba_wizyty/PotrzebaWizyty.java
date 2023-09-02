package com.magisterka.potrzeba_wizyty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotrzebaWizyty {
    private Long id;
    private String details;
    private Long patientId;
}
