package com.magisterka.ankieta_wstepna;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

import java.util.List;

@Secured({SecurityRoles.PATIENT, SecurityRoles.DOCTOR})
@Controller("/blood-types")
public class BloodTypeController {
    @Get
    public List<BloodType> getBloodTypes() {
        return List.of(BloodType.values());
    }
}
