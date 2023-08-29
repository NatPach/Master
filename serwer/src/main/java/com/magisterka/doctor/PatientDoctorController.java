package com.magisterka.doctor;

import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Secured(SecurityRoles.PATIENT)
@Controller
@AllArgsConstructor
public class PatientDoctorController {
    private final DoctorRepository doctorRepository;

    @Get("/doctors")
    public List<Doctor> getDoctors() {
        return StreamSupport.stream(doctorRepository.findAll().spliterator(), false)
                .map(entity -> new Doctor(entity.getId(), String.join(" ", Stream.of(entity.getFirstName(), entity.getLastName()).filter(Predicate.not(String::isBlank)).toList())))
                .toList();
    }
}
