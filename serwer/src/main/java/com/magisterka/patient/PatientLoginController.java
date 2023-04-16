package com.magisterka.patient;

import com.magisterka.security.BaseLoginController;
import com.magisterka.security.LoginSource;
import io.micronaut.http.annotation.Controller;

@Controller("/patient/login")
public class PatientLoginController extends BaseLoginController {
    @Override
    protected LoginSource loginSource() {
        return LoginSource.PATIENT;
    }
}
