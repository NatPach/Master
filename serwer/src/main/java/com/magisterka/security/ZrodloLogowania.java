package com.magisterka.security;

public enum ZrodloLogowania {
    LEKARZ,
    PACJENT;

    public static final String REQUEST_ATTRIBUTE = "zrodlo-logowania";
}
