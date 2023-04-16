package com.magisterka.patient;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    Optional<PatientEntity> find(String username);
}
