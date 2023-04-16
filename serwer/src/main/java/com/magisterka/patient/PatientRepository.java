package com.magisterka.patient;

import com.magisterka.doctor.DoctorEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> find(String username);
}
