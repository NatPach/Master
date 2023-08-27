package com.magisterka.ankieta_wstepna;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BloodType {
    @JsonProperty("0 Rh+")
    ZERO_PLUS,
    @JsonProperty("0 Rh-")
    ZERO_MINUS,
    @JsonProperty("A Rh+")
    A_PLUS,
    @JsonProperty("A Rh-")
    A_MINUS,
    @JsonProperty("B Rh+")
    B_PLUS,
    @JsonProperty("B Rh-")
    B_MINUS,
    @JsonProperty("AB Rh+")
    AB_PLUS,
    @JsonProperty("AB Rh-")
    AB_MINUS,
}
