package com.magisterka.doctor;

import com.magisterka.security.BaseLoginController;
import com.magisterka.security.LoginSource;
import io.micronaut.http.annotation.Controller;

@Controller("/doctor/login")
public class DoctorLoginController extends BaseLoginController {
    @Override
    protected LoginSource loginSource() {
        return LoginSource.DOCTOR;
    }
}
