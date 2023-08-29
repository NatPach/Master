package com.magisterka.ankieta_wstepna;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

import java.util.List;

@Secured({SecurityRoles.PATIENT, SecurityRoles.DOCTOR})
@Controller("/tryby-zycia")
public class TrybZyciaController {
    @Get
    public List<TrybZycia> getTrybyZycia() {
        return List.of(TrybZycia.values());
    }
}
