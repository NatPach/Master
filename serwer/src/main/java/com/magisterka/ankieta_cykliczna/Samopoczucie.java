package com.magisterka.ankieta_cykliczna;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Samopoczucie {
    @JsonProperty("lepsze")
    LEPSZE,
    @JsonProperty("gorsze")
    GORSZE,
    @JsonProperty("bez zmian")
    BEZ_ZMIAN
}
