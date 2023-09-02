package com.magisterka.potrzeba_wizyty;

import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import java.util.List;

@Secured({SecurityRoles.PATIENT, SecurityRoles.DOCTOR})
@Controller("/potrzeby-wizyty")
public class PotrzebaWizytyController {
    @Inject
    private PotrzebaWizytyService service;
    @Inject
    private AuthAttributesProvider authAttributesProvider;

    @Get
    public List<PotrzebaWizyty> getPotrzebyWizyty() {
        return service.getNotClosedPotrzebyWizyty(getContext());
    }

    private PotrzebaWizytyContext getContext() {
        if (authAttributesProvider.getDoctorId() != null) {
            return new PotrzebaWizytyContext.PotrzebaWizytyDlaLekarza(authAttributesProvider.getDoctorId());
        }
        if (authAttributesProvider.getPatientId() != null) {
            return new PotrzebaWizytyContext.PotrzebaWizytyDlaPacjenta(authAttributesProvider.getPatientId());
        }
        throw new IllegalStateException("Nie rozpoznano zalogowanego użytkownika. Nie można pobrać listy potrzeb wizyt.");
    }
}
