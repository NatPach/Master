package com.magisterka.ankieta_cykliczna;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UwagaZdrowotna {
    @JsonProperty("potrzeba wizyty")
    POTRZEBA_WIZYTY,
    @JsonProperty("spadek wagi")
    SPADEK_WAGI,
    @JsonProperty("przybranie wagi")
    PRZYBRANIE_WAGI,
    @JsonProperty("leki pominiete")
    LEKI_POMINIETE
}
