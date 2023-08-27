package com.magisterka.ankieta_wstepna;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TrybZycia {
    @JsonProperty("aktywny")
    AKTYWNY,
    @JsonProperty("siedzacy")
    SIEDZACY
}
