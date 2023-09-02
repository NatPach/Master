package com.magisterka.patient;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Patient {
    Long id;
    String firstName;
    String middleName;
    String lastName;
    String phone;
    String email;
    String pesel;
}
