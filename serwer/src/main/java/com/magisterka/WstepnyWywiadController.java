package com.magisterka;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class WstepnyWywiadController {
    @Get("/wstepny-wywiad")
    public WstepnyWywiad pobierzWstepnyWywiad (){
        return new WstepnyWywiad(50d, 175d, "AB Rh-");
    }
}
