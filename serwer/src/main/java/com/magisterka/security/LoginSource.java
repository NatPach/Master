package com.magisterka.security;

public enum LoginSource {
    DOCTOR,
    PATIENT;

    public static final String REQUEST_ATTRIBUTE = "custom-login-source";
}
