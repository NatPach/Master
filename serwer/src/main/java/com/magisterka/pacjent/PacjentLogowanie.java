package com.magisterka.pacjent;

import com.magisterka.security.Logowanie;
import com.magisterka.security.ZrodloLogowania;
import io.micronaut.http.annotation.Controller;

@Controller("/pacjent/logowanie")
public class PacjentLogowanie extends Logowanie {
    @Override
    protected ZrodloLogowania zrodloLogowania() {
        return ZrodloLogowania.PACJENT;
    }
}
