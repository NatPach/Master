package com.magisterka.lekarz;

import com.magisterka.security.Logowanie;
import com.magisterka.security.ZrodloLogowania;
import io.micronaut.http.annotation.Controller;

@Controller("/lekarz/logowanie")
public class LekarzLogowanie extends Logowanie {
    @Override
    protected ZrodloLogowania zrodloLogowania() {
        return ZrodloLogowania.LEKARZ;
    }
}
