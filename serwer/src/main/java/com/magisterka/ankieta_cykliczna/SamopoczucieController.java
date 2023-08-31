package com.magisterka.ankieta_cykliczna;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

import java.util.List;

@Secured({SecurityRoles.PATIENT, SecurityRoles.DOCTOR})
@Controller("/samopoczucia")
public class SamopoczucieController {
    @Get
    public List<Samopoczucie> getSamopoczucia() {
        return List.of(Samopoczucie.values());
    }
}
